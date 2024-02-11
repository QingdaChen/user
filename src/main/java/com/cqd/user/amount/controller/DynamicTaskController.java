package com.cqd.user.amount.controller;

import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.common.error.Error;
import com.cqd.user.amount.common.utils.R;
import com.cqd.user.amount.entity.TaskEntity;
import com.cqd.user.amount.service.DynamicTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: crush
 * @Date: 2021-07-29 15:26
 * version 1.0
 */
@RestController
@RequestMapping("/dynamicTask")
public class DynamicTaskController {

    @Autowired
    private DynamicTaskService dynamicTaskService;

    /**
     * 查看已开启但还未执行的动态任务
     *
     * @return
     */
    @GetMapping
    public List<String> getStartingDynamicTask() {
        return dynamicTaskService.getTaskList();
    }


    /**
     * 开启一个动态任务
     *
     * @param task
     * @return
     */
    @PostMapping("/dynamic")
    public R startDynamicTask(@RequestBody TaskEntity task) {
        // 将这个添加到动态定时任务中去
        dynamicTaskService.add(task);
        return Error.handle(BizCodeEnum.SUCCESS, "动态任务:" + task.getName() + " 已开启");
    }


    /**
     * 根据名称 停止一个动态任务
     *
     * @param name
     * @return
     */
    @DeleteMapping("/{name}")
    public R stopDynamicTask(@PathVariable("name") String name) {
        // 将这个添加到动态定时任务中去
        BizCodeEnum codeEnum = dynamicTaskService.stop(name);
        return Error.handle(codeEnum, "任务已停止");
    }


}
