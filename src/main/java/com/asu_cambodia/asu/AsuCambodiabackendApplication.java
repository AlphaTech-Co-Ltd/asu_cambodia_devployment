package com.asu_cambodia.asu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsuCambodiabackendApplication {

    public static void main(String[] args) {

        // Load environment variables directly from system (works on Railway and Docker)
        System.setProperty("spring.datasource.url", System.getenv("DATASOURCE_URL"));
        System.setProperty("spring.datasource.username", System.getenv("DATASOURCE_USER"));
        System.setProperty("spring.datasource.password", System.getenv("DATASOURCE_PASSWORD"));
        System.setProperty("FRONTEND_URL", System.getenv("FRONTEND_URL"));
        System.setProperty("jwt.secret", System.getenv("JWT_SECRETKEY"));

        SpringApplication.run(AsuCambodiabackendApplication.class, args);
    }

}

