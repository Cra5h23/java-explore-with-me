package ru.practicum.participation.service;

import ru.practicum.participation.dto.ParticipationRequestDto;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public interface PrivateParticipationRequestService {
    List<ParticipationRequestDto> getParticipationRequests(Long userId);

    ParticipationRequestDto addParticipationRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);
}
