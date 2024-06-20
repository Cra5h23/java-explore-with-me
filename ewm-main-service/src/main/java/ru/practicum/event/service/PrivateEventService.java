package ru.practicum.event.service;

import ru.practicum.dto.event.EventDtoRequest;
import ru.practicum.dto.event.EventFullDtoResponse;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.UpdateEventUserRequest;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public interface PrivateEventService {
    List<EventShortDto> getUserEvents(Long userId, long from, long size);

    Object addEvent(Long userId, EventDtoRequest event);

    EventFullDtoResponse getUserEvent(Long userId, Long eventId);

    EventFullDtoResponse updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest event);
}
