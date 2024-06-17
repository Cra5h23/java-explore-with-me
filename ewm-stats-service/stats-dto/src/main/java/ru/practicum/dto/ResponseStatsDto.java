package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 17.06.2024
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatsDto {
    private String app;
    private String uri;
    private long hits;
}
