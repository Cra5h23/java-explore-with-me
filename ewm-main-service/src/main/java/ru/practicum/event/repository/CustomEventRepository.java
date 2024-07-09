package ru.practicum.event.repository;

import ru.practicum.event.repository.projection.EventShortProjection;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
public interface CustomEventRepository {
    List<EventShortProjection> findEventsWithinRadius(double lat, double lon, double radius);
}
