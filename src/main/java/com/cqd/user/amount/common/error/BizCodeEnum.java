package com.cqd.user.amount.common.error;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002：短信验证码频率太高
 *  15: 用户
 */
public enum BizCodeEnum {
    SUCCESS(0,"请求成功"),
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    //定时任务相关
    CRON_STOP_ERROR(12001,"停止失败,任务已在进行中"),
    TASK_NOT_EXISTS(12002,"任务不存在"),

    //用户额度信息相关
    USER_AMOUNT_NOT_EXISTS(15001,"用户额度信息不存在"),
    AMOUNT_CREATE_ERROR(15002,"用户额度信息保存错误"),
    UPDATE_ERROR(15003,"用户额度更新错误"),
    UNBOUND_LIMIT(15004,"超出额度限制"),;

    private int code;
    private String msg;
    BizCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
