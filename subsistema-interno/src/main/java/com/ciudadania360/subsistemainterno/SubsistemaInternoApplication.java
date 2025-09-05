package com.ciudadania360.subsistemainterno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ciudadania360.subsistemainterno")
public class SubsistemaInternoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubsistemaInternoApplication.class, args);
    }
}
