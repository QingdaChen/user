package com.cqd.user.amount.common.constant;

public enum TaskTypeEnum {
    CREATE(0),
    UPDATE(1),
    DELETE(2);
    private int value;

    TaskTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
