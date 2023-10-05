package com.gothub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class GothubApplication {

    public static void main(String[] args) {
        SpringApplication.run(GothubApplication.class, args);
    }
}

