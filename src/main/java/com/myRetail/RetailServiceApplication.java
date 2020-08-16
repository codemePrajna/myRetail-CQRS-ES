package com.myRetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.myRetail.repository"})
@EnableWebSecurity
@EnableSwagger2
public class RetailServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RetailServiceApplication.class, args);
    }
}
