package com.cqd.user.amount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.common.utils.PageUtils;
import com.cqd.user.amount.entity.AmountEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AmountService extends IService<AmountEntity> {
    AmountEntity getAmountByUserId(String userId);

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询额度信息
     */
    PageUtils queryPageByCondition(Map<String, Object> params);

    /**
     * 保存额度信息
     */
    boolean saveAmount(AmountEntity amount);

    /**
     * 更新额度
     */
    boolean update(String userId,Double changeAmount);

    /**
     * 批量删除额度信息
     */
    BizCodeEnum deleteBatch(List<Long> userIds);

    /**
     * 批量更新额度信息
     */
    BizCodeEnum updateBatchByUserIds(List<AmountEntity> amountEntities);

    BizCodeEnum changeAmountBatch(List<AmountEntity> amountEntities, Double changeAmount, Double maxAmount, Double minAmount);

    BizCodeEnum create(AmountEntity amount);
}
