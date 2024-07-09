package ru.practicum.event.repository;

import ru.practicum.event.dto.EventState;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.projection.EventShortProjection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<EventShortProjection> findEventsWithinRadius(double lat, double lon, double radius) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventShortProjection> cq = cb.createQuery(EventShortProjection.class);
        Root<Event> eventRoot = cq.from(Event.class);

        // Создание функции расстояния
        Expression<Double> distance = cb.function(
                "distance",
                Double.class,
                cb.literal(lat),
                cb.literal(lon),
                eventRoot.get("location").get("lat"),
                eventRoot.get("location").get("lon")
        );

        // Условие для расстояния и статуса события
        Predicate distancePredicate = cb.lessThanOrEqualTo(distance, radius);
        Predicate statusPredicate = cb.equal(eventRoot.get("state"), EventState.PUBLISHED);

        // Выборка полей, соответствующих интерфейсу EventShort
        cq.multiselect(
                        eventRoot.get("id").alias("id"),
                        eventRoot.get("title").alias("title"),
                        eventRoot.get("annotation").alias("annotation"),
                        eventRoot.get("category").alias("category"),
                        eventRoot.get("eventDate").alias("eventDate"),
                        eventRoot.get("initiator").alias("initiator"),
                        eventRoot.get("paid").alias("paid"),
                        eventRoot.get("publishedOn").alias("publishedOn"),
                        eventRoot.get("confirmedRequests").alias("confirmedRequests") // Прямое использование поля с аннотацией @Formula
                )
                .where(cb.and(distancePredicate, statusPredicate));

        return entityManager.createQuery(cq).getResultList();
    }
}
