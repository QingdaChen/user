package com.cqd.user.amount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务
 *      1、@EnableScheduling 开启定时任务
 *      2、@Scheduled  开启一个定时任务
 *      3、自动配置类 TaskSchedulingAutoConfiguration
 **/
@EnableScheduling
@SpringBootApplication
public class UserAmountApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAmountApplication.class, args);
    }

}
