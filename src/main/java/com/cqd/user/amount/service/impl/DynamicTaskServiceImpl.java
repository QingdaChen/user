package com.cqd.user.amount.service.impl;

import cn.hutool.core.convert.ConverterRegistry;
import com.cqd.user.amount.common.constant.AmountConstant;
import com.cqd.user.amount.common.constant.StatusEnum;
import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.entity.AmountEntity;
import com.cqd.user.amount.entity.TaskEntity;
import com.cqd.user.amount.service.AmountService;
import com.cqd.user.amount.service.DynamicTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author crush
 */
@Service("dynamicTaskService")
@Slf4j
public class DynamicTaskServiceImpl implements DynamicTaskService {

    /**
     * 以下两个都是线程安全的集合类。
     */
    public Map<String, TaskEntity> taskMap = new ConcurrentHashMap<>();
    public List<String> taskList = new CopyOnWriteArrayList<>();

    @Autowired
    private ThreadPoolTaskScheduler syncScheduler;

    @Autowired
    private AmountService amountService;

    @Override
    public ThreadPoolTaskScheduler getSyncScheduler() {
        return syncScheduler;
    }

    /**
     * 查看已开启但还未执行的动态任务
     *
     * @return
     */
    @Override
    public List<String> getTaskList() {
        return taskList;
    }

    @Override
    public Map<String, TaskEntity> getTaskMap() {
        return taskMap;
    }

    /**
     * 添加一个动态任务
     *
     * @param task
     * @return
     */
    @Override
    public BizCodeEnum add(TaskEntity task) {
        // 此处的逻辑是 ，如果当前已经有这个名字的任务存在，先删除之前的，再添加现在的。（即重复就覆盖）
        if (null != taskMap.get(task.getName())) {
            stop(task.getName());
        }

        // hutool 工具包下的一个转换类型工具类 好用的很
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        Date startTime = converterRegistry.convert(Date.class, task.getStart());
        task.setStartTime(startTime);

        // schedule :调度给定的Runnable ，在指定的执行时间调用它。
        //一旦调度程序关闭或返回的ScheduledFuture被取消，执行将结束。
        //参数：
        //任务 – 触发器触发时执行的 Runnable
        //startTime – 任务所需的执行时间（如果这是过去，则任务将立即执行，即尽快执行）
        //ScheduledFuture<?> schedule = syncScheduler.schedule(getRunnable(task), startTime);
        taskMap.put(task.getName(), task);
        taskList.add(task.getName());
        return BizCodeEnum.SUCCESS;
    }


    /**
     * 运行任务
     *
     * @param task
     * @return
     */
    @Override
    public Runnable getRunnable(TaskEntity task) {
        return () -> {
            log.info("---动态定时任务运行---");
            try {
                log.info("执行动态定时任务: {} 此时时间: {},此任务执行周期由数据库中的cron表达式决定",
                        task.getName(), LocalDateTime.now().toLocalTime());
                List<AmountEntity> amountEntities = task.getAmountEntities();
                switch (task.getType()) {
                    case CREATE:
                        for (AmountEntity amount : amountEntities) {
                            if (amount.getMaxLimit() == null) {
                                amount.setMaxLimit(AmountConstant.AMOUNT_LIMIT);
                            }
                            amount.setCreateTime(new Date());
                            amount.setUpdateTime(new Date());
                            amount.setStatus(StatusEnum.EXIST.getValue());
                        }
                        boolean ok = amountService.saveBatch(amountEntities);
                        if (!ok) {
                            log.error("定时任务执行出错:{}", task);
                        }
                        break;
                    case UPDATE:
                        BizCodeEnum codeEnum = amountService.updateBatchByUserIds(amountEntities);
                        if (BizCodeEnum.SUCCESS.getCode() != codeEnum.getCode()) {
                            log.error("定时任务执行出错:{}", task);
                        }
                        break;
                    case DELETE:
                        List<Long> userIds = new ArrayList<>();
                        for (AmountEntity amount : amountEntities) {
                            userIds.add(amount.getUserId());
                        }
                        codeEnum = amountService.deleteBatch(userIds);
                        if (BizCodeEnum.SUCCESS.getCode() != codeEnum.getCode()) {
                            log.error("定时任务执行出错:{}", task);
                        }
                        break;
                    case CHANGE:
                        codeEnum = amountService.changeAmountBatch(amountEntities,
                                task.getChangeAmount(),task.getMaxAmount(),task.getMinAmount());
                        if (BizCodeEnum.SUCCESS.getCode() != codeEnum.getCode()) {
                            log.error("定时任务执行出错:{}", task);
                        }
                        break;
                    default:

                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.error("定时任务执行出错:{}", task);
            }
            log.info("---end--------");
        };
    }

    /**
     * 停止任务
     *
     * @param name
     * @return
     */
    @Override
    public BizCodeEnum stop(String name) {
        if (null == taskMap.get(name)) {
            return BizCodeEnum.TASK_NOT_EXISTS;
        }
        taskMap.remove(name);
        taskList.remove(name);
        return BizCodeEnum.SUCCESS;
    }


}