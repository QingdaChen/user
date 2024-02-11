package com.cqd.user.amount.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 订单
 *
 * @author cqd
 * @email chenqingda@gmail.com
 * @date 2024-02-07 09:56:16
 */
@Data
@TableName("user_amount")
public class AmountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * user_id
     */
    private Long userId;
    /**
     * 额度
     */
    private Double amount;
    /**
     * 限额
     */
    private Double maxLimit;
    /**
     * 状态【 1:存在 0:已删除】
     */
    private Integer status;
    /**
     * create_time
     */
    private Date createTime;
    /**
     * update_time
     */
    private Date updateTime;



}
