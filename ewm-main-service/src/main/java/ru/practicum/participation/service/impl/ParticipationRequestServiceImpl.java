package ru.practicum.participation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EventRequestStatus;
import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.participation.dto.ParticipationRequestDto;
import ru.practicum.exception.NotFoundParticipationRequestException;
import ru.practicum.participation.mapper.ParticipationRequestMapper;
import ru.practicum.participation.model.ParticipationRequest;
import ru.practicum.participation.repository.ParticipationRequestRepository;
import ru.practicum.participation.service.ParticipationRequestService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 27.06.2024
 */
@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestMapper participationRequestMapper;

    @Override
    public ParticipationRequest checkParticipationRequest(Long partId) {
        return participationRequestRepository.findById(partId)
                .orElseThrow(() -> new NotFoundParticipationRequestException(
                        String.format("Запрос с id %d не найден или не доступен", partId)));
    }

    @Override
    public ParticipationRequest checkParticipationRequest(Long partId, Long userId) {
        return participationRequestRepository.findByIdAndRequesterId(partId, userId)
                .orElseThrow(() -> new NotFoundParticipationRequestException(
                        String.format("Запрос с id %d у пользователя с id %d не найден или недоступен", partId, userId)));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        return participationRequestRepository.findAllByEventInitiatorIdAndEventId(userId, eventId)
                .stream()
                .map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult confirmUserRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        participationRequestRepository.updateStatusForRequests(request.getStatus(), request.getRequestIds(), eventId, userId);
        var eventRequests = getEventRequests(userId, eventId);
        var confirmed = eventRequests.stream()
                .filter(r -> r.getStatus().equals(EventRequestStatus.CONFIRMED))
                .collect(Collectors.toList());

        var rejected = eventRequests.stream()
                .filter(r -> r.getStatus().equals(EventRequestStatus.REJECTED))
                .collect(Collectors.toList());

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed)
                .rejectedRequests(rejected)
                .build();
    }
}
