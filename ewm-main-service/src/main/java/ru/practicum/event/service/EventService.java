package ru.practicum.event.service;

import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
public interface EventService {
    Event checkEvent(Long eventId);

    Event checkEvent(Long eventId, Long initiatorId);

    Map<Long, Long> getViews(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<String> uris);

    void saveStats(HttpServletRequest request, List<String> uris, String appName);

    void saveStats(HttpServletRequest request, String uri, String appName);

    long getViews(LocalDateTime rangeStart, LocalDateTime rangeEnd, String uri);

    List<EventShortDto> getEvents(Collection<Long> events);

    Map<Long, List<EventShortDto>> getEvents(Map<Long, List<Long>> compilationsEvents);

    List<EventShortDto> getEventsByLocation(double lat, double lon, double radius);
}
