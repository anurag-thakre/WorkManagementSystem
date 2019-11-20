package com.ffma.workmanagement.entiry;

import java.security.InvalidParameterException;

public enum TaskStatus {

    COMPLETED("Completed");

    private final String value;

    private TaskStatus(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static TaskStatus fromValue(String value) {
        for (TaskStatus role : values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        throw new InvalidParameterException(value);
    }
}