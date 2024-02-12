package com.cqd.user.amount.config;

import com.cqd.user.amount.service.CronService;
import com.cqd.user.amount.service.DynamicTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
public class CompleteScheduleConfig implements SchedulingConfigurer {


    @Autowired
    private CronService conService;

    @Autowired
    private DynamicTaskService dynamicTaskService;
    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                    scheduler();
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = conService.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.isBlank(cron)) {
                        log.error("获取定时任务表达式失败");
                    }
                    log.info("定时任务表达式: {}"+cron);
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );

    }

    /*
       执行任务
     */
    public void scheduler(){
        dynamicTaskService.getTaskMap().forEach((taskName,task)->{
            dynamicTaskService.getSyncScheduler().
                    schedule(dynamicTaskService.getRunnable(task),task.getStartTime());
        });
    }



}