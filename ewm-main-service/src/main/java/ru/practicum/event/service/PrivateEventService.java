package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.participation.dto.ParticipationRequestDto;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public interface PrivateEventService {
    List<EventShortDto> getUserEvents(Long userId, int from, int size);

    EventFullDtoResponse addEvent(Long userId, EventDtoRequest dto);

    EventFullDtoResponse getUserEvent(Long userId, Long eventId);

    EventFullDtoResponse updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest dto);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult confirmUserRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request);
}
