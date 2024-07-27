package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 09.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserLocationDtoResponse {
    private Long id;
    private Float lon;
    private Float lat;
    private List<EventShortDto> events;
}
