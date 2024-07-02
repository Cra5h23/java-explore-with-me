package ru.practicum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.practicum.converter.CustomStringToLocalDateTimeConverter;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String dateTimeFormat;

    public WebConfig(@Value("${datetime.format}") String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    @Bean
    public CustomStringToLocalDateTimeConverter customStringToLocalDateTimeConverter() {
        return new CustomStringToLocalDateTimeConverter(dateTimeFormat);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(customStringToLocalDateTimeConverter());
    }
}
