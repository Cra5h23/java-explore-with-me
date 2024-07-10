package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdminLocationDtoResponse extends UserLocationDtoResponse {
    private Float radius;
    private String name;
    private String description;
}
