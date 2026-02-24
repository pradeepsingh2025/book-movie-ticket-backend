package me.bookyourshow.backend.configurations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.bookyourshow.backend.enums.DaysEnum;

import java.io.IOException;

public class DaysEnumSerializer extends JsonSerializer<DaysEnum> {
    @Override
    public void serialize(DaysEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.name());
        }
    }
}
