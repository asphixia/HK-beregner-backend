package com.example.hksalarycalculatorbackend;

import org.springframework.boot.SpringApplication;

public class TestHkSalaryCalculatorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(HkSalaryCalculatorBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
