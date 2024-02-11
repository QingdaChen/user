package com.cqd.user.amount;

import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.common.utils.PageUtils;
import com.cqd.user.amount.entity.AmountEntity;
import com.cqd.user.amount.service.AmountService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private AmountService amountService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    void contextLoads() {
    }

    @Test
    void testMysql(){
        AmountEntity entity = new AmountEntity();
        entity.setUserId(101l);
        entity.setMaxLimit(10000.0);
        entity.setStatus(1);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());

        amountService.save(entity);
    }

    @Test
    void testUpdate(){
        AmountEntity entity = new AmountEntity();
        entity.setUserId(101l);
        entity.setAmount(100.0);
        entity.setUpdateTime(new Date());
        entity.setStatus(1);
        entity.setMaxLimit(10000.0);
        List<AmountEntity> list = new ArrayList<>();
        list.add(entity);

        //AmountEntity amount = amountService.getAmountByUserId("101");
        //BizCodeEnum codeEnum = amountService.updateBatchByUserIds(list);
        //BizCodeEnum codeEnum = amountService.deleteBatch(new String[]{"101"});
        //AmountEntity amount = amountService.getAmountByUserId("101");
        HashMap<String, Object> m = new HashMap<>();
        m.put("page",1);
        m.put("limit",10);
        PageUtils pageUtils = amountService.queryPageByCondition(m);
        //logger.error("{}",codeEnum);
        logger.error("{}",pageUtils.getList());
    }

}
