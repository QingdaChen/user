package com.cqd.user.amount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqd.user.amount.common.constant.AmountConstant;
import com.cqd.user.amount.common.constant.StatusEnum;
import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.common.utils.PageUtils;
import com.cqd.user.amount.common.utils.Query;
import com.cqd.user.amount.common.utils.R;
import com.cqd.user.amount.common.utils.RRException;
import com.cqd.user.amount.dao.AmountDao;
import com.cqd.user.amount.entity.AmountEntity;
import com.cqd.user.amount.service.AmountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("amountService")
public class AmountServiceImpl extends ServiceImpl<AmountDao, AmountEntity> implements AmountService {

    @Override
    public AmountEntity getAmountByUserId(String userId) {
        Long uid = 0l;
        try {
            uid = Long.parseLong(userId);
        } catch (Exception e) {
            return null;
        }
        if (uid == 0l) {
            return null;
        }
        AmountEntity amount = this.baseMapper.getAmountByUserId(uid);
        return amount;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return null;
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<AmountEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.and((wrapper) -> {
            wrapper.eq("status", 1);
        });
        //指定最大额度查询
        String limitStr = (String) params.get("max_limit");
        if (!StringUtils.isEmpty(limitStr)) {
            Double limit;
            try {
                limit = Double.parseDouble(limitStr);
            } catch (Exception e) {
                return null;
            }
            queryWrapper.and((wrapper) -> {
                wrapper.le("max_limit", limit);
            });
        }

        IPage<AmountEntity> page = this.page(
                new Query<AmountEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public boolean saveAmount(AmountEntity amount) {
        return false;
    }

    @Override
    public boolean update(String userId, Double changeAmount) {
        return false;
    }

    @Override
    public BizCodeEnum deleteBatch(String[] userIds) {
        List<Long> ids = new ArrayList<>();
        for (String idStr : userIds) {
            long id = 0;
            try {
                id = Long.parseLong(idStr);
            } catch (Exception e) {
                return BizCodeEnum.VAILD_EXCEPTION;
            }
            ids.add(id);
        }
        this.baseMapper.deleteByUserIds(ids);
        return BizCodeEnum.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BizCodeEnum updateBatchByUserIds(List<AmountEntity> amountEntities) {
        List<Long> ids = new ArrayList<>();
        for (AmountEntity amount : amountEntities) {
            if (amount.getUserId() == 0 || amount.getStatus() == StatusEnum.NOT_EXIST.getValue() ||
                    amount.getAmount() < 0 || amount.getMaxLimit() < 0) {
                return BizCodeEnum.VAILD_EXCEPTION;
            }
            ids.add(amount.getUserId());
            amount.setUpdateTime(new Date());
            amount.setStatus(StatusEnum.EXIST.getValue());
        }
        List<AmountEntity> amounts = this.baseMapper.getAmountsByUserIds(ids);
        Map<Long, AmountEntity> amountMap = new HashMap<>();
        for (AmountEntity amount : amounts) {
            amountMap.put(amount.getUserId(), amount);
        }
        for (AmountEntity amount : amountEntities) {
            AmountEntity entity = amountMap.get(amount.getUserId());
            if (entity == null) {
                return BizCodeEnum.UPDATE_ERROR;
            }
            if (amount.getAmount() > entity.getMaxLimit()) {
                return BizCodeEnum.UNBOUND_LIMIT;
            }
        }
        for(AmountEntity amount:amountEntities){
            boolean ok = this.update(amount, new UpdateWrapper<AmountEntity>().eq("user_id", amount.getUserId()));
            if(!ok){
                throw new RRException("mysql update error");
            }
        }

        return BizCodeEnum.SUCCESS;
    }

    /*
        额度初始化
     */
    @Override
    public BizCodeEnum create(AmountEntity amount) {
        //校验参数
        if (amount.getUserId() == 0 || amount.getAmount() < 0) {
            return BizCodeEnum.VAILD_EXCEPTION;
        }
        amount.setMaxLimit(AmountConstant.AMOUNT_LIMIT);
        amount.setStatus(StatusEnum.EXIST.getValue());
        amount.setCreateTime(new Date());
        amount.setUpdateTime(new Date());
        boolean ok = this.save(amount);
        if (!ok) {
            return BizCodeEnum.AMOUNT_CREATE_ERROR;
        }
        return BizCodeEnum.SUCCESS;
    }
}
