package ru.practicum.participation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventRequestStatus;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.ConflictParticipationRequestException;
import ru.practicum.participation.dto.ParticipationRequestDto;
import ru.practicum.participation.mapper.ParticipationRequestMapper;
import ru.practicum.participation.repository.ParticipationRequestRepository;
import ru.practicum.participation.service.ParticipationRequestService;
import ru.practicum.participation.service.PrivateParticipationRequestService;
import ru.practicum.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateParticipationRequestServiceImpl implements PrivateParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestMapper participationRequestMapper;
    private final EventService eventService;
    private final UserService userService;
    private final ParticipationRequestService participationRequestService;

    /**
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getParticipationRequests(Long userId) {
        log.info("Получение списка запросов на участие для пользователя с id {}", userId);
        userService.checkUser(userId);
        return participationRequestRepository.findAllByRequesterId(userId)
                .stream()
                .map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    /**
     * @param userId
     * @param eventId
     * @return
     */
    @Override
    @Transactional
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        log.info("Добавление запроса на участие в событии с id {} от пользователя с id {}", eventId, userId);
        var event = eventService.checkEvent(eventId);

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictParticipationRequestException(String.format(
                    "Пользователь с id %d является инициатором события с id %d и не может добавлять запрос на участие " +
                            "в своём событии", userId, eventId));
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictParticipationRequestException(
                    String.format("Нельзя добавить запрос на участие в не опубликованном событии с id %d", eventId));
        }

        extracted(eventId, event);

        var status = EventRequestStatus.PENDING;

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            status = EventRequestStatus.CONFIRMED;
        }

        var participationRequest = participationRequestMapper
                .toParticipationRequest(userId, eventId, status);
        var save = participationRequestRepository.save(participationRequest);

        return participationRequestMapper.toParticipationRequestDto(save);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void extracted(Long eventId, Event event) {
        if (event.getParticipantLimit() != 0) {
            long l = event.getConfirmedRequests();
            if (l >= event.getParticipantLimit()) {
                throw new ConflictParticipationRequestException(String.format(
                        "Нельзя добавить запрос на участие в событии с id %d превышен лимит участников", eventId));
            }
        }
    }

    /**
     * @param userId
     * @param requestId
     * @return
     */
    @Override
    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        log.info("Отмена запроса на участие с id {} от пользователя с id {}", requestId, userId);
        var participationRequest = participationRequestService.checkParticipationRequest(requestId, userId);

        participationRequest.setStatus(EventRequestStatus.CANCELED);

        var save = participationRequestRepository.save(participationRequest);
        return participationRequestMapper.toParticipationRequestDto(save);
    }
}
