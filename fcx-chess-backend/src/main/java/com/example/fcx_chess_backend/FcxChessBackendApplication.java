package com.example.fcx_chess_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.meuprojetobackend")
public class FcxChessBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcxChessBackendApplication.class, args);
    }
}
