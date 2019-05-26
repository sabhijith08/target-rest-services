package com.redsky.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.redsky.client" })
public class RedskyClientApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RedskyClientApplication.class, args);
    }

}
