package com.mazzama.learn.newstopic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

public enum  Status {
    DRAFT ("Draft"),
    DELETED ("Deleted"),
    PUBLISH ("Publish");

    private String text;

    Status(String text) {
        this.text = text;
    }

    public String getStatus() {
        return text;
    }

    public static Status fromString(String statusString) {
        return Arrays.stream(values())
                .filter(value -> value.getStatus().equalsIgnoreCase(statusString))
                .findFirst()
                .orElse(null);
    }
}
