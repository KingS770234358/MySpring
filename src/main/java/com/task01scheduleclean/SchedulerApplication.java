package com.task01scheduleclean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.task01scheduleclean.dao")
@EnableTransactionManagement
@ComponentScan("com.task01scheduleclean.*")
public class SchedulerApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SchedulerApplication.class);
    }
}
