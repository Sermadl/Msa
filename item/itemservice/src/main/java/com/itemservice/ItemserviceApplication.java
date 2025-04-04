package com.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
public class ItemserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemserviceApplication.class, args);
    }

}
