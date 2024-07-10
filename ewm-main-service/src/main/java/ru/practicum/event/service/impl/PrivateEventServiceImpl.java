package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.event.service.PrivateEventService;
import ru.practicum.exception.ConflictEventException;
import ru.practicum.location.model.Location;
import ru.practicum.location.service.LocationService;
import ru.practicum.participation.dto.ParticipationRequestDto;
import ru.practicum.participation.service.ParticipationRequestService;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;
    private final LocationService locationService;
    private static final String URI_PATCH = "/events/";

    /**
     * @param userId
     * @param from
     * @param size
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getUserEvents(Long userId, int from, int size) {
        log.info("Запрошен список событий для пользователя инициатора событий с id {} и параметрами from={}, size={}",
                userId, from, size);
        var page = PageRequest.of(from / size, size);
        List<String> uris = new ArrayList<>();

        var events = eventRepository.findAllByInitiatorId(userId, page)
                .stream()
                .peek(e -> uris.add(URI_PATCH + e.getId()))
                .collect(Collectors.toList());

        if (events.isEmpty()) {
            return List.of();
        }

        Optional<ZonedDateTime> rangeStart = events.stream()
                .filter(e -> e.getPublishedOn() != null)
                .min(Comparator.comparing(EventRepository.EventShort::getPublishedOn))
                .map(EventRepository.EventShort::getPublishedOn);
        Map<Long, Long> views = new HashMap<>();

        if (rangeStart.isPresent()) {
            views = eventService.getViews(rangeStart.get().toLocalDateTime(), ZonedDateTime.now().toLocalDateTime(), uris);
        }

        return eventMapper.toListEventShortDto(events, views);
    }


    /**
     * @param userId
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public EventFullDtoResponse addEvent(Long userId, EventDtoRequest dto) {
        log.info("Попытка добавить новое событие {} для пользователя с id {}", dto, userId);
        var user = userService.checkUser(userId);
        var category = categoryService.checkCategory(dto.getCategory());
        var location = dto.getLocation();
        var event = eventMapper.toEvent(user, category, dto, getLocation(location));
        var save = eventRepository.save(event);
        log.info("Добавлено событие {}", save);

        return eventMapper.toEventFullDtoResponse(save, 0);
    }

    private Location getLocation(EventLocationDtoRequest location) {
        var id = location.getId();
        if (id != null) {
            return locationService.checkAdminLocation(id);
        } else {
            var lon = location.getLon();
            var lat = location.getLat();

            return locationService.addUserLocation(lon, lat);
        }
    }

    /**
     * @param userId
     * @param eventId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public EventFullDtoResponse getUserEvent(Long userId, Long eventId) {
        log.info("Запрос события с id {} для пользователя с id {}", eventId, userId);
        var event = eventService.checkEvent(eventId, userId);
        long views = 0;
        if (event.getState().equals(EventState.PUBLISHED)) {
            views = eventService.getViews(event.getPublishedOn().toLocalDateTime(), LocalDateTime.now(), URI_PATCH + eventId);
        }

        return eventMapper.toEventFullDtoResponse(event, views);
    }

    /**
     * @param userId
     * @param eventId
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public EventFullDtoResponse updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest dto) {
        log.info("Попытка обновить событие с id {} для пользователя с id {} , новые данные {}", eventId, userId, dto);
        var updatedEvent = eventService.checkEvent(eventId, userId);
        log.info("Старые данные {}", updatedEvent);

        if (updatedEvent.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictEventException(String.format(
                    "Нельзя обновить событие, событие с id %d уже опубликовано", eventId));
        }

        if (dto.getAnnotation() != null) {
            updatedEvent.setAnnotation(dto.getAnnotation());
        }

        if (dto.getEventDate() != null) {
            updatedEvent.setEventDate(dto.getEventDate().atZone(ZoneId.systemDefault()));
        }

        if (dto.getDescription() != null) {
            updatedEvent.setDescription(dto.getDescription());
        }

        if (dto.getPaid() != null) {
            updatedEvent.setPaid(dto.getPaid());
        }

        if (dto.getCategory() != null) {
            updatedEvent.setCategory(Category.builder()
                    .id(dto.getCategory())
                    .build());
        }

        if (dto.getRequestModeration() != null) {
            updatedEvent.setRequestModeration(dto.getRequestModeration());
        }

        if (dto.getTitle() != null) {
            updatedEvent.setTitle(dto.getTitle());
        }

        if (dto.getStateAction() != null) {
            switch (dto.getStateAction()) {
                case CANCEL_REVIEW:
                    updatedEvent.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    updatedEvent.setState(EventState.PENDING);
                    break;
            }
        }

        var location = dto.getLocation();
        if (location != null) {
            var locId = location.getId();

            if (locId != null) {
                var l = locationService.checkAdminLocation(locId);
                updatedEvent.setLocation(l);
            } else {
                var id = updatedEvent.getLocation().getId();
                var lat = location.getLat();
                var lon = location.getLon();

                var updateLocation = locationService.updateUserLocation(id, lon, lat);

                updatedEvent.setLocation(updateLocation);
            }
        }

        if (dto.getParticipantLimit() != null) {
            updatedEvent.setParticipantLimit(dto.getParticipantLimit());
        }
        var save = eventRepository.save(updatedEvent);
        long views = 0;
        if (save.getState().equals(EventState.PUBLISHED)) {
            views = eventService.getViews(save.getPublishedOn().toLocalDateTime(), LocalDateTime.now(), URI_PATCH + eventId);
        }

        return eventMapper.toEventFullDtoResponse(save, views);
    }

    /**
     * @param userId
     * @param eventId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        log.info("Получение запросов на участие в событии с id {} для пользователя инициатора с id {}", eventId, userId);
        return participationRequestService.getEventRequests(userId, eventId);
    }

    /**
     * @param userId
     * @param eventId
     * @param request
     * @return
     */
    @Override
    @Transactional
    public EventRequestStatusUpdateResult confirmUserRequests(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest request) {
        log.info("{} запросов c id {} на участие в событии с id {} для инициатора с id {}",
                request.getStatus().equals(EventRequestStatus.CONFIRMED) ? "Подтверждение" : "Отклонение",
                request.getRequestIds(), eventId, userId);

        return participationRequestService.confirmUserRequests(userId, eventId, request);
    }
}
