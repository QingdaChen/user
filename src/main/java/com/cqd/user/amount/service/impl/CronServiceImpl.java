package com.cqd.user.amount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqd.user.amount.dao.CronDao;
import com.cqd.user.amount.entity.CronEntity;
import com.cqd.user.amount.service.CronService;
import org.springframework.stereotype.Service;

@Service("cronServiceImpl")
public class CronServiceImpl extends ServiceImpl<CronDao, CronEntity> implements CronService {
    @Override
    public String getCron() {
        return this.baseMapper.getCron();
    }

}