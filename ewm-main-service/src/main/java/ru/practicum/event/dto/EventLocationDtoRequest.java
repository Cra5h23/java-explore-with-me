package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 09.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventLocationDtoRequest {
    private Long id;

    /**
     * Широта.
     */

    private Float lat;

    /**
     * Долгота.
     */

    private Float lon;
}
