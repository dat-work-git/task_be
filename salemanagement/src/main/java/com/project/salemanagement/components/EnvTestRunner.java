package com.project.salemanagement.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvTestRunner implements CommandLineRunner {


    @Value("${USERNAME_DB:postgres}")
    private String usernameDb;

    @Value("${PASSWORD_DB:2003}")
    private String passwordDb;

    @Value("${server.port:9001}")
    private int serverPort;


    @Value("${jwt.secretKey}")
    private String jwtSecret;
    

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== EnvTestRunner ===");
        System.out.println("JWT_SECRET Spring Boot thấy: " + jwtSecret);
        System.out.println("USERNAME_DB Spring Boot thấy: " + usernameDb);
        System.out.println("PASSWORD_DB Spring Boot thấy: " + passwordDb);
        System.out.println("Server Port Spring Boot thấy: " + serverPort);
    }
}

