package com.cqd.user.amount.common.constant;

public enum StatusEnum {
    EXIST(1),
    NOT_EXIST(0);
    private int value;

    StatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
