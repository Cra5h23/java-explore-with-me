package ru.practicum.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Nikolay Radzivon
 * @Date 28.06.2024
 */
public class CustomStringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

