package me.riazulislam.infinitecineplexbackend.configurations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;

import java.io.IOException;

public class DaysEnumDeserializer extends JsonDeserializer<DaysEnum> {
    @Override
    public DaysEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
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
