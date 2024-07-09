package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Marker;
import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDtoResponse {
    private Long id;

    private Float lon;

    private Float lat;

    private Float radius;
    private String name;

    private String description;

    private List<EventShortDto> events;
}
