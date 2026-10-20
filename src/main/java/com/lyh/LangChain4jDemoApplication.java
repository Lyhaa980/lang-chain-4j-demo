package com.lyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyh.mapper")
public class LangChain4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LangChain4jDemoApplication.class, args);
    }

}
