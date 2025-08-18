package com.asu_cambodia.asu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsuCambodiabackendApplication {

    public static void main(String[] args) {
        // Load .env file if exists (for local dev)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // won't crash if .env is missing
                .load();

        // Set system properties for Spring if not already set
        setIfMissing("spring.datasource.url", dotenv.get("DATASOURCE_URL", "jdbc:postgresql://localhost:5432/asu_cambodia_Db"));
        setIfMissing("spring.datasource.username", dotenv.get("DATASOURCE_USER", "postgres"));
        setIfMissing("spring.datasource.password", dotenv.get("DATASOURCE_PASSWORD", "password"));

        SpringApplication.run(AsuCambodiabackendApplication.class, args);
    }

    private static void setIfMissing(String key, String value) {
        if (System.getProperty(key) == null && System.getenv(key) == null) {
            System.setProperty(key, value);
        }
    }
}



