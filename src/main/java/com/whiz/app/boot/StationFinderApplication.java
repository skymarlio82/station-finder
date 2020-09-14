package com.whiz.app.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class StationFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationFinderApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        log.info("====> Application - Startup called :: java.io.tmpdir = {}", System.getProperty("java.io.tmpdir"));
        return args -> {

        };
    }
}