package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Nikolay Radzivon
 * @Date 10.07.2024
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EventAdminLocationDtoResponse extends EventUserLocationDtoResponse {
    private String name;
    private String description;
    private Float radius;
}
