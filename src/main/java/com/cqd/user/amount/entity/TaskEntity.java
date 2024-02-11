package com.cqd.user.amount.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskEntity {

    /**
     * 动态任务名曾
     */
    private String name;

    /**
     * 设定动态任务开始时间
     */
    private LocalDateTime start;

    /**
     * 任务类型 update create delete
     */
    private String type;


    private AmountEntity amount;
}
