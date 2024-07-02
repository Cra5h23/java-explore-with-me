package ru.practicum.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private final DateTimeFormatter formatter;

    public CustomLocalDateTimeSerializer(String dateTimeFormat) {
        super(LocalDateTime.class);
        formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.format(formatter));
    }
}
