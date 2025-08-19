package com.asu_cambodia.asu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsuCambodiabackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        // Set environment variables for Spring to read
        System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
        System.setProperty("POSTGRES_USER", dotenv.get("POSTGRES_USER"));
        System.setProperty("POSTGRES_PASSWORD", dotenv.get("POSTGRES_PASSWORD"));
        System.setProperty("JWT", dotenv.get("JWT"));

        SpringApplication.run(AsuCambodiabackendApplication.class, args);
    }
}



