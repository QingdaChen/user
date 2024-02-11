package com.cqd.user.amount.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqd.user.amount.entity.AmountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmountDao extends BaseMapper<AmountEntity> {
    AmountEntity getAmountByUserId(@Param("userId") Long userId);

    List<AmountEntity> getAmountsByUserIds(@Param("userIds") List<Long> userIds);

    void deleteByUserIds(@Param("userIds") List<Long> userIds);
}
