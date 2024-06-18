package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для сущности статистики посещений.
 *
 * @author Nikolay Radzivon
 * @Date 17.06.2024
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatsDto {
    /**
     * Название сервиса.
     */
    private String app;

    /**
     * URI сервиса.
     */
    private String uri;

    /**
     * Количество просмотров.
     */
    private long hits;
}
