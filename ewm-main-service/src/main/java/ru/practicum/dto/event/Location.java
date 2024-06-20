package ru.practicum.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для локации.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    /**
     * Широта.
     */
    private float lat;

    /**
     * Долгота.
     */
    private float lon;
}
