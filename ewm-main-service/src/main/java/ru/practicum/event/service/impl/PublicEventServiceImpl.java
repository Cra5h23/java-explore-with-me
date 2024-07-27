package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventFullDtoResponse;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.QEvent;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.projection.EventShortProjection;
import ru.practicum.event.service.EventService;
import ru.practicum.event.service.PublicEventService;
import ru.practicum.exception.NotFoundEventException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private static final String APP_NAME = "ewm-main-service";
    private static final String URI_PATCH = "/events/";

    /**
     * @param params
     * @return
     */
    @Override
    public List<EventShortDto> getEvents(GetEventsParams params, HttpServletRequest request) {
        log.info("Запрос списка событий с параметрами поиска {}", params);
        var query = QEvent.event.state.eq(EventState.PUBLISHED);
        var text = params.getText();
        var categories = params.getCategories();
        var paid = params.getPaid();
        var rangeStart = params.getRangeStart();
        var rangeEnd = params.getRangeEnd();
        var onlyAvailable = params.getOnlyAvailable();
        var sort = params.getSort();
        var from = params.getFrom();
        var size = params.getSize();

        if (text != null) {
            var q = QEvent.event.annotation.likeIgnoreCase(text)
                    .or(QEvent.event.description.likeIgnoreCase(text));
            query = query != null ? query.and(q) : q;
        }

        if (categories != null) {
            var q = QEvent.event.category.id.in(categories);
            query = query != null ? query.and(q) : q;
        }

        if (paid != null) {
            var q = QEvent.event.paid.eq(paid);
            query = query != null ? query.and(q) : q;
        }

        if (rangeStart != null && rangeEnd != null) {
            var q = QEvent.event.eventDate.between(rangeStart.atZone(ZoneId.systemDefault()),
                    rangeEnd.atZone(ZoneId.systemDefault()));
            query = query != null ? query.and(q) : q;
        }

        if (rangeStart == null || rangeEnd == null) {
            var q = QEvent.event.eventDate.after(ZonedDateTime.now());
            query = query != null ? query.and(q) : q;
        }

        if (onlyAvailable != null) {
            var q = QEvent.event.participantLimit.eq(0)
                    .or(QEvent.event.confirmedRequests.lt(QEvent.event.participantLimit));
            query = query != null ? query.and(q) : q;
        }

        var pageRequest = PageRequest.of(from / size, size);

        List<String> uris = new ArrayList<>();

        List<Event> collect = eventRepository.findAll(query, pageRequest).stream()
                .peek(e -> uris.add(URI_PATCH + e.getId()))
                .collect(Collectors.toList());

        Map<Long, Long> views = Map.of();
        if (rangeStart != null && rangeEnd != null) {
            views = eventService.getViews(rangeStart, rangeEnd, uris);
        }

        eventService.saveStats(request, uris, APP_NAME);
        var list = eventMapper.toListEventShortDtos(collect, views);

        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                    return list.stream()
                            .sorted(Comparator.comparing(EventShortDto::getEventDate))
                            .collect(Collectors.toList());
                case VIEWS:
                    return list.stream()
                            .sorted(Comparator.comparing(EventShortDto::getViews))
                            .collect(Collectors.toList());
            }
        }
        return list;
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @Override
    public EventFullDtoResponse getEvent(Long id, HttpServletRequest request) {
        log.info("Запрос события с id {}", id);
        var event = eventService.checkEvent(id);

        if (event.getState().equals(EventState.PUBLISHED)) {
            long views = eventService.getViews(event.getPublishedOn().toLocalDateTime(), LocalDateTime.now(), URI_PATCH + id);
            eventService.saveStats(request, URI_PATCH + id, APP_NAME);
            return eventMapper.toEventFullDtoResponse(event, views);
        } else {
            throw new NotFoundEventException(
                    String.format("Событие с id %d не найдено или не доступно", id));
        }
    }

    @Override
    public List<EventShortDto> searchEventsByLocation(GetSearchParams params) {
        log.info("Поиск событий по локации с параметрами поиска from={}, size={}, lon={}, lat={}, radius={}, eventStatus={}",
                params.getFrom(), params.getSize(), params.getLon(), params.getLat(), params.getRadius(), params.getEventStatus());

        List<EventShortProjection> content = eventRepository.findEventsWithinRadius(params).getContent();
        return eventService.getListEventShortDto(content);
    }
}
