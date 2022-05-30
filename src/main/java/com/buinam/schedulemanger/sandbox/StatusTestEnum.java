package com.buinam.schedulemanger.sandbox;

public enum StatusTestEnum {
    ACTIVE("Active"),
    DELETED("Deleted"),
    EDITED("Edited"),
    INACTIVE("InActive");
    private String status;

    StatusTestEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
