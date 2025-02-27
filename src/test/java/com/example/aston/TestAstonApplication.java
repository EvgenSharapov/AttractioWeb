package com.example.aston;

import org.springframework.boot.SpringApplication;

public class TestAstonApplication {

    public static void main(String[] args) {
        SpringApplication.from(AstonApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
