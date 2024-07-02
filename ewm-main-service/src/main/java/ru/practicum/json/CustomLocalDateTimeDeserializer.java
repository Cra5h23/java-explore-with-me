package ru.practicum.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    private final DateTimeFormatter formatter;

    public CustomLocalDateTimeDeserializer(String dateTimeFormat) {
        super(LocalDateTime.class);
        this.formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        return LocalDateTime.parse(date, formatter);
    }
}
