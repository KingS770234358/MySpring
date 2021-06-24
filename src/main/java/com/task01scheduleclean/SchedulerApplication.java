package com.task01scheduleclean;

import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Consumer;
import com.task01scheduleclean.service.DaoService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.task01scheduleclean.dao")
@EnableTransactionManagement
public class SchedulerApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SchedulerApplication.class);
    }
}
