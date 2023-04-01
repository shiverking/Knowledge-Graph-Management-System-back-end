package com.group.KGMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableScheduling
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class KGMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(KGMSApplication.class, args);
    }
}