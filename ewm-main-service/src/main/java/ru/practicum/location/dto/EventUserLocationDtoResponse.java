package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Dto для локации.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EventUserLocationDtoResponse {
    /**
     * Широта.
     */
    private float lat;

    /**
     * Долгота.
     */
    private float lon;
}
