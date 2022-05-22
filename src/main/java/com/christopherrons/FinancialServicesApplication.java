package com.christopherrons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableWebSocketMessageBroker
public class FinancialServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialServicesApplication.class, args);
    }

}
