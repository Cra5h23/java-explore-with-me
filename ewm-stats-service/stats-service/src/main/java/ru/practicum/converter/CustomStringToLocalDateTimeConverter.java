package ru.practicum.converter;

import org.springframework.core.convert.converter.Converter;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
public class CustomStringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        var decode = URLDecoder.decode(source, StandardCharsets.UTF_8);

        return LocalDateTime.parse(decode, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
