package me.bookyourshow.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShowStatusEnum {
    SCHEDULED,
    FULL,
    FILLING,
    COMPLETED,
    CANCELLED;

    @JsonCreator
    public static ShowStatusEnum from(String value) {
        return ShowStatusEnum.valueOf(value.toUpperCase());
    }
}
