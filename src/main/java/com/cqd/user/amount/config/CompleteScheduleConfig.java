package com.cqd.user.amount.config;

import com.cqd.user.amount.service.CronService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class CompleteScheduleConfig implements SchedulingConfigurer {


    @Autowired
    private CronService conService;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行动态定时任务1: " + LocalDateTime.now().toLocalTime() + ",此任务执行周期由数据库中的cron表达式决定"),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = conService.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.isBlank(cron)) {
                        log.error("获取定时任务表达式失败");
                    }
                    log.info("定时任务表达式: "+cron);
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

}