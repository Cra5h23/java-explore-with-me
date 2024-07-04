package ru.practicum.participation.repository;

import ru.practicum.event.dto.EventRequestStatus;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 28.06.2024
 */
public interface CustomParticipationRequestRepository {
    void updateStatusForRequests(EventRequestStatus status, List<Long> ids, Long eventId, Long initiatorId);
}
