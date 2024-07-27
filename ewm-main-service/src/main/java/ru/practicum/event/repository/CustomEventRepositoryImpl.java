package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.projection.EventShortProjection;
import ru.practicum.event.service.PublicEventService;
import ru.practicum.location.dto.EventSortType;
import ru.practicum.location.model.Location;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Page<EventShortProjection> findEventsWithinRadius(PublicEventService.GetSearchParams params) {
        Double lat = params.getLat();
        Double lon = params.getLon();
        Double radius = params.getRadius();
        int size = params.getSize();
        int from = params.getFrom();

        PageRequest pageable = PageRequest.of(from / size, size);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventShortProjection> cq = cb.createQuery(EventShortProjection.class);
        Root<Event> eventRoot = cq.from(Event.class);

        Expression<Double> distance = cb.function(
                "distance",
                Double.class,
                cb.literal(lat),
                cb.literal(lon),
                eventRoot.get("location").get("lat"),
                eventRoot.get("location").get("lon")
        );

        Predicate distancePredicate = cb.lessThanOrEqualTo(distance, radius);
        Predicate statusPredicate = cb.equal(eventRoot.get("state"), EventState.PUBLISHED);

        EventSortType eventStatus = params.getEventStatus();
        Predicate eventDate = cb.conjunction();
        switch (eventStatus) {
            case ALL:
                break;
            case PAST:
                eventDate = cb.lessThan(eventRoot.get("eventDate"), cb.currentDate());
                break;
            case UPCOMING:
                eventDate = cb.greaterThan(eventRoot.get("eventDate"), cb.currentDate());
                break;
        }

        cq.multiselect(
                        eventRoot.get("id").alias("id"),
                        eventRoot.get("title").alias("title"),
                        eventRoot.get("annotation").alias("annotation"),
                        eventRoot.get("category").alias("category"),
                        eventRoot.get("eventDate").alias("eventDate"),
                        eventRoot.get("initiator").alias("initiator"),
                        eventRoot.get("paid").alias("paid"),
                        eventRoot.get("publishedOn").alias("publishedOn"),
                        eventRoot.get("confirmedRequests").alias("confirmedRequests")
                )
                .where(cb.and(distancePredicate, statusPredicate, eventDate));

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Event> countRoot = countQuery.from(Event.class);
        Join<Event, Location> locationJoin = countRoot.join("location", JoinType.LEFT);
        Predicate distancePredicateForCount = cb.lessThanOrEqualTo(
                cb.function(
                        "distance",
                        Double.class,
                        cb.literal(lat),
                        cb.literal(lon),
                        locationJoin.get("lat"),
                        locationJoin.get("lon")
                ),
                radius
        );
        Predicate statusPredicateForCount = cb.equal(countRoot.get("state"), EventState.PUBLISHED);

        countQuery.select(cb.count(countRoot))
                .where(cb.and(distancePredicateForCount, statusPredicateForCount));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        TypedQuery<EventShortProjection> typedQuery = entityManager.createQuery(cq);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }
}
