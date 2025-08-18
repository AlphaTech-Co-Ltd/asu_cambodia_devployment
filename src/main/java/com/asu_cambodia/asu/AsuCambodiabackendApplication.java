package com.asu_cambodia.asu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsuCambodiabackendApplication {

    public static void main(String[] args) {

        String dbUrl = System.getenv("DATASOURCE_URL");
        String dbUser = System.getenv("DATASOURCE_USER");
        String dbPass = System.getenv("DATASOURCE_PASSWORD");
        String frontendUrl = System.getenv("FRONTEND_URL");
        String jwtSecret = System.getenv("JWT_SECRETKEY");

        if (dbUrl != null) System.setProperty("spring.datasource.url", dbUrl);
        if (dbUser != null) System.setProperty("spring.datasource.username", dbUser);
        if (dbPass != null) System.setProperty("spring.datasource.password", dbPass);
        if (frontendUrl != null) System.setProperty("FRONTEND_URL", frontendUrl);
        if (jwtSecret != null) System.setProperty("jwt.secret", jwtSecret);

        SpringApplication.run(AsuCambodiabackendApplication.class, args);
    }
}


