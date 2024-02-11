package com.cqd.user.amount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqd.user.amount.entity.AmountEntity;
import com.cqd.user.amount.entity.CronEntity;

public interface CronService extends IService<CronEntity>{
    String getCron();
}
