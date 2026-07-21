package com.springcloud.springcloud.models;

public enum RepplyStatus {
    SUCCESS,
    ERROR;

    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
