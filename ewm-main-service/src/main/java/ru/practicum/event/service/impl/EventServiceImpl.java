package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.NotFoundEventException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final StatsClient statsClient;
    private final EventMapper eventMapper;
    private static final String URI_PATCH = "/events/";

    @Override
    public Event checkEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundEventException(
                        String.format("Событие с id %d не найдено или не доступно", eventId)));
    }

    @Override
    public Event checkEvent(Long eventId, Long initiatorId) {
        return eventRepository.findByIdAndInitiatorId(eventId, initiatorId)
                .orElseThrow(() -> new NotFoundEventException(String.format(
                        "Событие с id %d для пользователя с id %d не найдено или не доступно", eventId, initiatorId)));
    }

    @Override
    public Map<Long, Long> getViews(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<String> uris) {
        if (rangeStart == null && rangeEnd == null) {
            return Map.of();
        }

        var stats = statsClient.getStats(rangeStart, rangeEnd, uris, true);
        var body = stats.getBody();

        return body != null ? body.stream()
                .collect(Collectors.toMap(r ->
                                Long.parseLong(r.getUri().substring(r.getUri().lastIndexOf("/") + 1)),
                        ResponseStatsDto::getHits)) : Map.of();
    }

    @Override
    public void saveStats(HttpServletRequest request, List<String> uris, String appName) {
        var ip = request.getRemoteAddr();
        uris.forEach(uri -> statsClient.saveStats(ip, uri, appName));
    }

    @Override
    public void saveStats(HttpServletRequest request, String uri, String appName) {
        saveStats(request, List.of(uri), appName);
    }


    @Override
    public long getViews(LocalDateTime rangeStart, LocalDateTime rangeEnd, String uri) {
        var views = getViews(rangeStart, rangeEnd, List.of(uri));

        if (views == null) {
            return 0;
        }

        return views.getOrDefault(Long.parseLong(uri.substring(uri.lastIndexOf("/") + 1)), 0L);
    }

    @Override
    public List<EventShortDto> getEvents(Collection<Long> events) {
        List<Event> allById = eventRepository.findAllById(events);
        List<String> uris = new ArrayList<>();

        Optional<ZonedDateTime> rangeStart = allById.stream()
                .peek(event -> uris.add(URI_PATCH + event.getId()))
                .filter(e -> e.getPublishedOn() != null)
                .min(Comparator.comparing(Event::getPublishedOn))
                .map(Event::getPublishedOn);

        Map<Long, Long> views = rangeStart.isPresent() ? getViews(rangeStart.get().toLocalDateTime(),
                LocalDateTime.now(), uris) : Map.of();

        return eventMapper.toListEventShortDtos(allById, views);
    }

    @Override
    public Map<Long, List<EventShortDto>> getEvents(Map<Long, List<Long>> compilationsEvents) {
        Map<Long, List<EventShortDto>> eventsMap = new HashMap<>();

        for (Long l : compilationsEvents.keySet()) {
            List<Long> longs = compilationsEvents.get(l);
            List<EventShortDto> events = getEvents(longs);

            eventsMap.put(l, events);
        }

        return eventsMap;
    }
}