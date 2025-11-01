BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo "Deploying with branch: $BRANCH"

cd app/task_be

git fetch -a
git checkout $BRANCH
git pull


docker compose -f docker-compose.yml down

if docker image inspect task_be:latest >/dev/null 2>&1; then
  docker tag task_be:latest task_be:previous
fi

docker compose -f docker-compose.yml pull task_be
docker compose -f docker-compose.yml up -d task_be

sleep 10  # đợi container khởi động
CONTAINER_STATUS=$(docker inspect -f '{{.State.Status}}' task_be)
if [ "$CONTAINER_STATUS" != "running" ]; then
  echo "Container crashed! Rolling back..."
  docker compose -f docker-compose.yml up -d task_be:previous #rollback
  exit 1
fi
docker system prune -af