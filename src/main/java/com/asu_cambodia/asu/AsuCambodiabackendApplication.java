package com.asu_cambodia.asu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsuCambodiabackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("spring.datasource.url", dotenv.get("DATASOURCE_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DATASOURCE_USER"));
        System.setProperty("spring.datasource.password", dotenv.get("DATASOURCE_PASSWORD"));
        System.setProperty("FRONTEND_URL", dotenv.get("FRONTEND_URL"));
        System.setProperty("jwt.secret", dotenv.get("JWT_SECRETKEY"));


        SpringApplication.run(AsuCambodiabackendApplication.class, args);
    }

}
