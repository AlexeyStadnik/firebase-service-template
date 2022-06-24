package com.service.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TemplateApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }

}
