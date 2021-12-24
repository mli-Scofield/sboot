package com.dtc233.sboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbootApplication.class, args);
    }

}
