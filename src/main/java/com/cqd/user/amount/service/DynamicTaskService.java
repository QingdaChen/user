package com.cqd.user.amount.service;

import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.entity.TaskEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DynamicTaskService {
    ThreadPoolTaskScheduler getSyncScheduler();

    List<String> getTaskList();
    Runnable getRunnable(TaskEntity task);

    Map<String, TaskEntity> getTaskMap();

    BizCodeEnum add(TaskEntity task);
    BizCodeEnum stop(String name);

}
