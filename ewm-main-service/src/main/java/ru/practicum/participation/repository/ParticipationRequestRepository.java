package ru.practicum.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.participation.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long>,
        CustomParticipationRequestRepository {

    List<ParticipationRequest> findAllByRequesterId(Long requesterId);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long partId, Long userId);

    List<ParticipationRequest> findAllByEventInitiatorIdAndEventId(Long requesterId, Long eventId);
}
