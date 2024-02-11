package com.cqd.user.amount.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_cron")
public class CronEntity {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * cron_expression
     */
    private String cronExpression;
    /**
     * cron_describe
     */
    private String cronDescribe;
}
