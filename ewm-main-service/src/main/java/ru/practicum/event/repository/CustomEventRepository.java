package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import ru.practicum.event.repository.projection.EventShortProjection;
import ru.practicum.event.service.PublicEventService;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
public interface CustomEventRepository {
    Page<EventShortProjection> findEventsWithinRadius(PublicEventService.GetSearchParams params);
}
