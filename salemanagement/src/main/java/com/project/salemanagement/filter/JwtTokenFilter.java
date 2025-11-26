package com.project.salemanagement.filter;

import com.project.salemanagement.components.JwtTokenUtil;
import com.project.salemanagement.models.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Pair;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // create reuqest id
            String requestId = UUID.randomUUID().toString();
            MDC.put("requestId", requestId);

            // get ip
            String ip = request.getRemoteAddr();

            // get request detail
            HttpServletRequest req = (HttpServletRequest) request;
            // request start
            long start = System.currentTimeMillis();
            log.info("<= {} {} {}ms [{}]", req.getMethod(), req.getRequestURI(), Instant.now(), requestId);

            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (!authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);
                final String email = jwtTokenUtil.extractEmail(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<String> roles = jwtTokenUtil.extractRoles(token);
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    log.info("Authenticating user={} with roles={}", email, roles);

                    if (jwtTokenUtil.validTokenExpired(token)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(email, null, authorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);

            // request end
            long time = System.currentTimeMillis() - start;
            log.info("<= {} {} {}ms [{}]", req.getMethod(), req.getRequestURI(), time, requestId);
        } catch (
                Exception e) {
            log.error("Unauthorized access: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } finally {
            MDC.clear();

        }
    }

    private boolean isByPassToken(@NonNull HttpServletRequest request) {
        System.out.println("path: "+request.getServletPath()+" method "+ request.getMethod());
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of("salemanagement/v1/resetPassword/renderOtp", "POST"),
                Pair.of("salemanagement/v1/resetPassword/confirmOtp", "POST"),
                Pair.of("salemanagement/v1/user/login", "POST")
                );
        for (Pair<String, String> byPassToken : byPassTokens) {
            if (request.getServletPath().contains(byPassToken.getLeft())
                    && request.getMethod().equals(byPassToken.getRight())) {
                return true;
            }
        }
        return false;
    }
}
