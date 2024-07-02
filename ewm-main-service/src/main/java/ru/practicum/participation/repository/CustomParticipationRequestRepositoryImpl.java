package ru.practicum.participation.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventRequestStatus;
import ru.practicum.exception.ConflictParticipationRequestException;
import ru.practicum.participation.model.ParticipationRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 28.06.2024
 */
public class CustomParticipationRequestRepositoryImpl implements CustomParticipationRequestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void updateStatusForRequests(EventRequestStatus status, List<Long> ids, Long eventId, Long initiatorId) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(ParticipationRequest.class);
        var pr = cq.from(ParticipationRequest.class);
        var idPredicate = pr.get("id").in(ids);
        var eventPredicate = cb.equal(pr.get("event").get("id"), eventId);
        var initiatorPredicate = cb.equal(pr.get("event").get("initiator").get("id"), initiatorId);

        cq.select(pr).where(cb.and(idPredicate, eventPredicate, initiatorPredicate));

        var requests = entityManager.createQuery(cq).getResultList();

        requests.forEach(request -> {
            if (request.getStatus().equals(EventRequestStatus.CONFIRMED) && status.equals(EventRequestStatus.REJECTED)) {
                throw new ConflictParticipationRequestException(String.format(
                        "Нельзя отменить уже принятую заявку c id %d", request.getId()));
            }
            if (request.getEvent().getConfirmedRequests() == request.getEvent().getParticipantLimit()) {
                throw new ConflictParticipationRequestException(String.format(
                        "Нельзя принять заявку с id %d, у события с id %d достигнут лимит участников",
                        request.getId(), request.getEvent().getId()));
            }

            request.setStatus(status);
            entityManager.merge(request);
        });

        entityManager.flush();
    }
}
