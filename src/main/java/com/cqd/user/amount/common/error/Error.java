package com.cqd.user.amount.common.error;

import com.cqd.user.amount.common.utils.R;

public class Error {
    public static R handle(BizCodeEnum code,String msg){
        switch (code){
            case UNKNOW_EXCEPTION:
                return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(),
                        BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
            case VAILD_EXCEPTION:
                return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(),
                        BizCodeEnum.VAILD_EXCEPTION.getMsg());
            case USER_AMOUNT_NOT_EXISTS:
                return R.error(BizCodeEnum.USER_AMOUNT_NOT_EXISTS.getCode(),
                        BizCodeEnum.USER_AMOUNT_NOT_EXISTS.getMsg());
            case AMOUNT_CREATE_ERROR:
                return R.error(BizCodeEnum.AMOUNT_CREATE_ERROR.getCode(),
                        BizCodeEnum.AMOUNT_CREATE_ERROR.getMsg());
            case UPDATE_ERROR:
                return R.error(BizCodeEnum.UPDATE_ERROR.getCode(),
                        BizCodeEnum.UPDATE_ERROR.getMsg());
            case UNBOUND_LIMIT:
                return R.error(BizCodeEnum.UNBOUND_LIMIT.getCode(),
                        BizCodeEnum.UNBOUND_LIMIT.getMsg());
            default:
                return R.ok(msg);
        }

    }
}
