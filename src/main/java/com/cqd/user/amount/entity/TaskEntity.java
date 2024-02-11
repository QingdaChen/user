package com.cqd.user.amount.entity;

import com.cqd.user.amount.common.constant.TaskTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

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
    private TaskTypeEnum type;


    private List<AmountEntity> amountEntities;
}
