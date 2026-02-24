package me.riazulislam.infinitecineplexbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SeatStatusEnum {
    ACTIVE,
    INACTIVE,
    DAMAGED;

    @JsonCreator
    public static SeatStatusEnum from(String value) {
        return SeatStatusEnum.valueOf(value.toUpperCase());
    }
}
