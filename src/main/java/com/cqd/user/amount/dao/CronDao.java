package com.cqd.user.amount.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqd.user.amount.entity.AmountEntity;
import com.cqd.user.amount.entity.CronEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CronDao extends BaseMapper<CronEntity> {
    String getCron();
}
