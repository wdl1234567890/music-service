package com.fl.wdl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class ApplicationContext
{
    public static void main(String[] args) {
        SpringApplication.run(ApplicationContext.class, args);
    }
}
