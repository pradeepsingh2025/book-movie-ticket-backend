package me.riazulislam.infinitecineplexbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShowStatusEnum {
    SCHEDULED,
    COMPLETED,
    CANCELLED;

    @JsonCreator
    public static ShowStatusEnum from(String value) {
        return ShowStatusEnum.valueOf(value.toUpperCase());
    }
}
