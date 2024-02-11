package com.cqd.user.amount.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 异步线程池ThreadPoolExecutor 配置类
 *
 * @Author: crush
 * @Date: 2021-07-23 14:14
 */
@Configuration
public class ThreadPoolTaskExecutorConfig {

    @Bean
    public ThreadPoolTaskScheduler syncScheduler() {
        ThreadPoolTaskScheduler syncScheduler = new ThreadPoolTaskScheduler();
        syncScheduler.setPoolSize(5);
        syncScheduler.setThreadGroupName("syncTg");
        syncScheduler.setThreadNamePrefix("syncThread-");
        syncScheduler.initialize();
        return syncScheduler;
    }
}
