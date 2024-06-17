package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 17.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatsDto {
    private String ip;
    private String uri;
    private String app;
    private String timestamp;
}
