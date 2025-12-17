package com.example.pptupload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.pptupload.mapper")
public class PptUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(PptUploadApplication.class, args);
    }
}
