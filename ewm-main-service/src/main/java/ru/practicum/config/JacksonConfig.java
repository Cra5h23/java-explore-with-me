package ru.practicum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.json.CustomLocalDateTimeDeserializer;
import ru.practicum.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 28.06.2024
 */
@Configuration
public class JacksonConfig {
    @Value("${datetime.format}")
    private String dataTimeFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.deserializerByType(LocalDateTime.class, new CustomLocalDateTimeDeserializer(dataTimeFormat));
            builder.serializerByType(LocalDateTime.class, new CustomLocalDateTimeSerializer(dataTimeFormat));
        };
    }
}
