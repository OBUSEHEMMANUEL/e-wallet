package com.example.ewallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableMongoRepositories
@EnableAsync
public class EWalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(EWalletApplication.class, args);
    }

}

//RCP_2m6q4na72rgry92