package ru.practicum.event.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.dto.event.EventDtoRequest;
import ru.practicum.dto.event.EventFullDtoResponse;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.UpdateEventUserRequest;
import ru.practicum.event.service.PrivateEventService;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Service
public class PrivateEventServiceImpl implements PrivateEventService {
    /**
     * @param userId
     * @param from
     * @param size
     * @return
     */
    @Override
    public List<EventShortDto> getUserEvents(Long userId, long from, long size) {
        return List.of();
    }

    /**
     * @param userId
     * @param event
     * @return
     */
    @Override
    public EventFullDtoResponse addEvent(Long userId, EventDtoRequest event) {
        return null;
    }

    /**
     * @param userId
     * @param eventId
     * @return
     */
    @Override
    public EventFullDtoResponse getUserEvent(Long userId, Long eventId) {
        return null;
    }

    /**
     * @param userId
     * @param eventId
     * @param event
     * @return
     */
    @Override
    public EventFullDtoResponse updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest event) {
        return null;
    }

    /**
     * @param userId
     * @param eventId
     * @return
     */
    @Override
    public ParticipationRequestDto getEventRequests(Long userId, Long eventId) {
        return null;
    }

    /**
     * @param userId
     * @param eventId
     * @param request
     * @return
     */
    @Override
    public EventRequestStatusUpdateResult confirmUserRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        return null;
    }
}
