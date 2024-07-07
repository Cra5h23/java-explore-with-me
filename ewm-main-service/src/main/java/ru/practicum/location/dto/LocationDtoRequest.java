package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDtoRequest {
    private Float lon;
    private Float lat;
    private Float radius;
    private String name;
    private String description;
}
