package ru.practicum.participation.service;

import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.participation.dto.ParticipationRequestDto;
import ru.practicum.participation.model.ParticipationRequest;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 27.06.2024
 */
public interface ParticipationRequestService {
    ParticipationRequest checkParticipationRequest(Long partId);

    ParticipationRequest checkParticipationRequest(Long partId, Long userId);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult confirmUserRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request);
}


