package com.cqd.user.amount.entity;

import com.cqd.user.amount.common.constant.TaskTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class TaskEntity implements Serializable {

    /**
     * 动态任务名曾
     */
    private String name;

    /**
     * 设定动态任务开始时间
     */
    private LocalDateTime start;

    /**
     * 任务类型 0:create 1:update 2:delete 3:change
     */
    private TaskTypeEnum type;

    @JsonProperty(value = "amount_entities")
    private List<AmountEntity> amountEntities;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * amount改变值
     */
    @JsonProperty(value = "change_amount")
    private Double changeAmount;

    @JsonProperty(value = "max_amount")
    private Double maxAmount;

    @JsonProperty(value = "min_amount")
    private Double minAmount;
}
