package ru.practicum.event.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDtoResponse;
import ru.practicum.event.dto.EventPublisedState;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.QEvent;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.AdminEventService;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.ConflictEventException;

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
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private static final String URI_PATCH = "/events/";

    /**
     * @param params
     * @return
     */
    @Override
    public List<EventFullDtoResponse> getEvents(GetEventsParams params) {
        if (params == null) {
            return List.of();
        }

        BooleanExpression query = Expressions.asBoolean(true).isTrue();
        ;

        var users = params.getUsers();
        if (users != null) {
            query = QEvent.event.initiator.id.in(users);
        }

        var states = params.getStates();
        if (states != null) {
            var q = QEvent.event.state.in(states);
            query = query != null ? query.and(q) : q;
        }

        var categories = params.getCategories();
        if (categories != null) {
            var q = QEvent.event.category.id.in(categories);
            query = query != null ? query.and(q) : q;
        }

        var rangeStart = params.getRangeStart();
        var rangeEnd = params.getRangeEnd();
        if (rangeStart != null && rangeEnd != null) {
            var q = QEvent.event.eventDate
                    .between(rangeStart.atZone(ZoneId.systemDefault()), rangeEnd.atZone(ZoneId.systemDefault()));

            query = query != null ? query.and(q) : q;
        }

        int from = params.getFrom();
        int size = params.getSize();
        var pageRequest = PageRequest.of(from / size, size);
        List<String> uris = new ArrayList<>();
        var collect = eventRepository.findAll(query, pageRequest).stream()
                .peek(e -> uris.add(URI_PATCH + e.getId()))
                .collect(Collectors.toList());
        Map<Long, Long> views = Map.of();

        if (rangeStart != null && rangeEnd != null) {
            views = eventService.getViews(rangeStart, rangeEnd, uris);
        }

        if (rangeStart == null && rangeEnd == null && !collect.isEmpty()) {
            var start = collect.stream()
                    .filter(e -> e.getPublishedOn() != null)
                    .min(Comparator.comparing(Event::getPublishedOn))
                    .map(Event::getPublishedOn);

            if (start.isPresent()) {
                views = eventService.getViews(start.get().toLocalDateTime(), LocalDateTime.now(), uris);
            }
        }

        return eventMapper.toListEventFullDto(collect, views);
    }

    /**
     * @param eventId
     * @param event
     * @return
     */
    @Override
    public EventFullDtoResponse updateEvent(Long eventId, UpdateEventAdminRequest event) {
        var updatedEvent = eventService.checkEvent(eventId);
        var stateAction = event.getStateAction();

        if (stateAction != null) {
            var eventState = updatedEvent.getState();

            if (eventState.equals(EventState.PUBLISHED) && stateAction.equals(EventPublisedState.PUBLISH_EVENT)) {
                throw new ConflictEventException(String.format(
                        "Нельзя опубликовать событие, событие с id %d уже опубликовано", eventId));
            }

            if (eventState.equals(EventState.CANCELED) && stateAction.equals(EventPublisedState.PUBLISH_EVENT)) {
                throw new ConflictEventException(String.format(
                        "Нельзя опубликовать событие, событие с id %d отменено", eventId));
            }

            if (eventState.equals(EventState.PUBLISHED) && stateAction.equals(EventPublisedState.REJECT_EVENT)) {
                throw new ConflictEventException(String.format(
                        "Нельзя отменить событие, событие с id %d опубликовано", eventId));
            }

            switch (stateAction) {
                case PUBLISH_EVENT:
                    updatedEvent.setState(EventState.PUBLISHED);
                    updatedEvent.setPublishedOn(ZonedDateTime.now());
                    break;
                case REJECT_EVENT:
                    updatedEvent.setState(EventState.PENDING);
                    break;
            }
        }


        if (event.getAnnotation() != null && !updatedEvent.getAnnotation().equals(event.getAnnotation())) {
            updatedEvent.setAnnotation(event.getAnnotation());
        }

        if (event.getCategory() != null && !updatedEvent.getCategory().getId().equals(event.getCategory())) {
            updatedEvent.setCategory(Category.builder()
                    .id(event.getCategory())
                    .build());
        }

        if (event.getDescription() != null && !updatedEvent.getDescription().equals(event.getDescription())) {
            updatedEvent.setDescription(event.getDescription());
        }

        if (event.getEventDate() != null) {
            updatedEvent.setEventDate(event.getEventDate().atZone(ZoneId.systemDefault()));
        }

        if (event.getLocation() != null) {
            updatedEvent.setLocation(Location.builder()
                    .lon(event.getLocation().getLon())
                    .lat(event.getLocation().getLat())
                    .build());
        }

        if (event.getPaid() != null && updatedEvent.isPaid() != event.getPaid()) {
            updatedEvent.setPaid(event.getPaid());
        }

        if (event.getParticipantLimit() != null && updatedEvent.getParticipantLimit() != event.getParticipantLimit()) {
            updatedEvent.setParticipantLimit(event.getParticipantLimit());
        }

        if (event.getRequestModeration() != null && updatedEvent.isRequestModeration() != event.getRequestModeration()) {
            updatedEvent.setRequestModeration(event.getRequestModeration());
        }

        if (event.getStateAction() != null) {
            switch (event.getStateAction()) {
                case PUBLISH_EVENT:
                    updatedEvent.setState(EventState.PUBLISHED);
                    updatedEvent.setPublishedOn(ZonedDateTime.now());
                    break;
                case REJECT_EVENT:
                    updatedEvent.setState(EventState.CANCELED);
                    break;
            }
        }

        if (event.getTitle() != null && !updatedEvent.getTitle().equals(event.getTitle())) {
            updatedEvent.setTitle(event.getTitle());
        }

        var save = eventRepository.save(updatedEvent);

        long views = 0;
        if (save.getState().equals(EventState.PUBLISHED)) {
            views = eventService.getViews(save.getPublishedOn().toLocalDateTime(), LocalDateTime.now(),
                    URI_PATCH + save.getId());
        }

        return eventMapper.toEventFullDtoResponse(save, views);
    }
}
