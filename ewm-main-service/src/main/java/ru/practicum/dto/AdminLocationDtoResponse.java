package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLocationDtoResponse {
    private Long id;
    private Float lat;
    private Float lon;
    private Float radius;
    private String name;
    private String description;
}
