package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestStatsDto {
    private String aps;
    private String uri;
    private String ip;
    private String timestamp;
}
