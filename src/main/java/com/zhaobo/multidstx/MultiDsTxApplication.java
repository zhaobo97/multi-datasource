package com.zhaobo.multidstx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.zhaobo.multidstx")
public class MultiDsTxApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDsTxApplication.class, args);
    }

}
