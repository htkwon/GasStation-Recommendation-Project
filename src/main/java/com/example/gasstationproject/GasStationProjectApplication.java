package com.example.gasstationproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GasStationProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GasStationProjectApplication.class, args);
    }

}
