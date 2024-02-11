package com.cqd.user.amount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.entity.TaskEntity;

import java.util.List;

public interface DynamicTaskService {
    List<String> getTaskList();
    Runnable getRunnable(TaskEntity task);
    BizCodeEnum add(TaskEntity task);
    BizCodeEnum stop(String name);
}
