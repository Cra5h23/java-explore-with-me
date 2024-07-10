package ru.practicum.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventDtoRequest;
import ru.practicum.event.dto.EventFullDtoResponse;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.projection.EventShortProjection;
import ru.practicum.location.dto.EventAdminLocationDtoResponse;
import ru.practicum.location.dto.EventUserLocationDtoResponse;
import ru.practicum.location.model.Location;
import ru.practicum.location.model.TypeLocation;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@Component
public class EventMapper {
    private EventUserLocationDtoResponse makeLocation(Location location) {
        TypeLocation type = location.getType();

        if (type == TypeLocation.USERS) {
            return EventUserLocationDtoResponse.builder()
                    .lat(location.getLat())
                    .lon(location.getLon())
                    .build();
        } else {
            return EventAdminLocationDtoResponse.builder()
                    .lat(location.getLat())
                    .lon(location.getLon())
                    .name(location.getName())
                    .radius(location.getRadius())
                    .description(location.getDescription())
                    .build();
        }
    }

    public EventFullDtoResponse toEventFullDtoResponse(Event event, long views) {
        return EventFullDtoResponse.builder()
                .location(makeLocation(event.getLocation()))
                .id(event.getId())
                .participantLimit(event.getParticipantLimit())
                .description(event.getDescription())
                .eventDate(event.getEventDate().toLocalDateTime())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .createdOn(event.getCreatedOn().toLocalDateTime())
                .annotation(event.getAnnotation())
                .category(CategoryDtoResponse.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .views(views)
                .title(event.getTitle())
                .paid(event.isPaid())
                .confirmedRequests(event.getConfirmedRequests())
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn().toLocalDateTime() : null)
                .build();
    }

    public List<EventShortDto> toListEventShortDto(List<EventRepository.EventShort> eventShortList,
                                                   Map<Long, Long> viewsMap) {
        if (eventShortList.isEmpty()) {
            return List.of();
        }

        List<EventShortDto> list = new ArrayList<>();

        for (EventRepository.EventShort eventShort : eventShortList) {
            Long l = viewsMap.getOrDefault(eventShort.getId(), 0L);

            list.add(toEventShortDto(eventShort, l));
        }
        return list;
    }

    public List<EventShortDto> toListEventShortDtoaa(List<EventShortProjection> eventShortList,
                                                     Map<Long, Long> viewsMap) {
        if (eventShortList.isEmpty()) {
            return List.of();
        }

        List<EventShortDto> list = new ArrayList<>();

        for (EventShortProjection eventShort : eventShortList) {
            Long l = viewsMap.getOrDefault(eventShort.getId(), 0L);

            list.add(toEventShortDto(eventShort, l));
        }
        return list;
    }


    public List<EventShortDto> toListEventShortDtos(List<Event> eventList, Map<Long, Long> viewsMap) {
        if (eventList.isEmpty()) {
            return List.of();
        }

        List<EventShortDto> list = new ArrayList<>();

        for (Event event : eventList) {
            Long l = viewsMap.getOrDefault(event.getId(), 0L);

            list.add(toEventShortDto(event, l));
        }
        return list;
    }


    public EventShortDto toEventShortDto(EventRepository.EventShort eventShort, Long views) {
        return EventShortDto.builder()
                .id(eventShort.getId())
                .title(eventShort.getTitle())
                .confirmedRequests(eventShort.getConfirmedRequests())
                .eventDate(eventShort.getEventDate().toLocalDateTime())
                .annotation(eventShort.getAnnotation())
                .category(CategoryDtoResponse.builder()
                        .id(eventShort.getCategory().getId())
                        .name(eventShort.getCategory().getName())
                        .build())
                .initiator(UserShortDto.builder()
                        .id(eventShort.getInitiator().getId())
                        .name(eventShort.getInitiator().getName())
                        .build())
                .paid(eventShort.getPaid())
                .views(views)
                .build();
    }

    public EventShortDto toEventShortDto(Event event, Long views) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().toLocalDateTime())
                .annotation(event.getAnnotation())
                .category(CategoryDtoResponse.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.isPaid())
                .views(views)
                .build();
    }

    public EventShortDto toEventShortDto(EventShortProjection event, Long views) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().toLocalDateTime())
                .annotation(event.getAnnotation())
                .category(CategoryDtoResponse.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.isPaid())
                .views(views)
                .build();
    }

    public List<EventFullDtoResponse> toListEventFullDto(List<Event> collect, Map<Long, Long> viewsMap) {
        if (collect == null) {
            return List.of();
        }

        List<EventFullDtoResponse> list = new ArrayList<>();

        for (Event event : collect) {
            var views = viewsMap != null ? viewsMap.getOrDefault(event.getId(), 0L) : 0;
            list.add(toEventFullDtoResponse(event, views));
        }
        return list;
    }

    public Event toEvent(User user, Category category, EventDtoRequest dto, Location location) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(category)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate().atZone(ZoneId.systemDefault()))
                .paid(dto.isPaid())
                .location(location)
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .initiator(user)
                .createdOn(ZonedDateTime.now())
                .state(EventState.PENDING)
                .publishedOn(null)
                .build();
    }
}
