package com.cqd.user.amount.controller;

import com.cqd.user.amount.common.error.BizCodeEnum;
import com.cqd.user.amount.common.error.Error;
import com.cqd.user.amount.common.utils.PageUtils;
import com.cqd.user.amount.common.utils.R;
import com.cqd.user.amount.entity.AmountEntity;
import com.cqd.user.amount.service.AmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 额度
 *
 * @author cqd
 * @email chenqingda@gmail.com
 * @date 2024-02-07 09:56:16
 */
@RestController
@RequestMapping("user/amount")
public class AmountController {
    @Autowired
    private AmountService amountService;


    /**
     * 分页批量查询额度结果
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = amountService.queryPageByCondition(params);
        if (page == null) {
            return R.error(BizCodeEnum.USER_AMOUNT_NOT_EXISTS.getCode(),
                    BizCodeEnum.USER_AMOUNT_NOT_EXISTS.getMsg());
        }
        return R.ok().put("page", page);
    }


    /**
     * 查询用户额度信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") String userId) {
        AmountEntity amount = amountService.getAmountByUserId(userId);
        if (amount == null) {
            return R.error(BizCodeEnum.USER_AMOUNT_NOT_EXISTS.getCode(),
                    BizCodeEnum.USER_AMOUNT_NOT_EXISTS.getMsg());
        }
        return R.ok().put("amount", amount);
    }

    /**
     * 初始化额度
     */
    @RequestMapping("/create")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody AmountEntity amount) {
        BizCodeEnum codeEnum = amountService.create(amount);
        return Error.handle(codeEnum,"");
    }

    /**
     * 修改额度
     */
    @PostMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody() Map<String,List<AmountEntity>> paramMap) {
        List<AmountEntity> amountEntities = paramMap.get("amount_entities");
        BizCodeEnum codeEnum = amountService.updateBatchByUserIds(amountEntities);
        return Error.handle(codeEnum,"");
    }

    /**
     * 批量根据用户id列表删除额度信息
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Map<String,List<Long>> paramMap) {
        List<Long> userIds = paramMap.get("user_ids");
        BizCodeEnum codeEnum = amountService.deleteBatch(userIds);
        return Error.handle(codeEnum,"");
    }

}
