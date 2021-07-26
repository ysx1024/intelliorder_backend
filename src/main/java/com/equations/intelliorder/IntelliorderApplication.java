package com.equations.intelliorder;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@MapperScan("com.equations.intelliorder.*.mapper")
public class IntelliorderApplication {

    public static void main(String[] args) {
        //主程序入口
        SpringApplication.run(IntelliorderApplication.class, args);

    }

}
