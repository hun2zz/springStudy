package com.study.springStudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.study.springStudy")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}