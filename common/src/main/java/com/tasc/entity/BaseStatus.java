package com.tasc.entity;

public enum BaseStatus {
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active"),
    PENDING(2, "Pending"),
    BLOCKED(3, "Blocked")
    ;
    public int code;
    public String status;

    BaseStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
