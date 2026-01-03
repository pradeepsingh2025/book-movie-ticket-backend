package me.riazulislam.infinitecineplexbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.riazulislam.infinitecineplexbackend.configurations.DaysEnumDeserializer;
import me.riazulislam.infinitecineplexbackend.configurations.DaysEnumSerializer;

@JsonSerialize(using = DaysEnumSerializer.class)
@JsonDeserialize(using = DaysEnumDeserializer.class)
public enum DaysEnum {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday;

    @JsonCreator
    public static DaysEnum fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            return DaysEnum.valueOf(value);
        } catch (IllegalArgumentException e) {
            // Try case-insensitive match
            for (DaysEnum day : DaysEnum.values()) {
                if (day.name().equalsIgnoreCase(value)) {
                    return day;
                }
            }
            throw new IllegalArgumentException("Invalid day value: " + value);
        }
    }
}
