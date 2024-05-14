package com.bookstack.bookstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
@TestConfiguration(proxyBeanMethods = false)
public class TestBookStackApplication {

    public static void main(String[] args) {
        SpringApplication.from(BookStackApplication::main).with(TestBookStackApplication.class).run(args);
    }

}
