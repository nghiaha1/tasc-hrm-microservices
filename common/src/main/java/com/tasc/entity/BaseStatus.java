package com.tasc.entity;

public enum BaseStatus {
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active"),
    PENDING(2, "Pending"),
    BLOCKED(3, "Blocked")
    ;
    public int statusCode;
    public String status;

    BaseStatus(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }
}
