package ru.practicum.location.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.repository.projection.EventShortProjection;
import ru.practicum.location.model.TypeLocation;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 09.07.2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationFullProjection {
    private Long id;
    private String name;
    private float lat;
    private float lon;
    private Float radius;
    private String description;
    private TypeLocation type;
    private List<EventShortProjection> events;
}