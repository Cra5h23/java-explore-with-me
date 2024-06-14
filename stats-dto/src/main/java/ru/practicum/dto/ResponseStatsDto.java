package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseStatsDto {
    private String app;
    private String uri;
    private long hits;
}
