package ru.practicum.location.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.projection.EventShortProjection;
import ru.practicum.location.dto.EventSortType;
import ru.practicum.location.model.Location;
import ru.practicum.location.repository.projection.LocationFullProjection;
import ru.practicum.location.service.PublicLocationService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Nikolay Radzivon
 * @Date 09.07.2024
 */
public class CustomLocationRepositoryImpl implements CustomLocationRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Page<LocationFullProjection> findLocations(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);
        CriteriaQuery<Location> select = cq.select(root);

        TypedQuery<Location> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Location> listLocations = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Location> countRoot = countQuery.from(Location.class);
        countQuery.select(cb.count(countRoot));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        List<LocationFullProjection> loc = new ArrayList<>();

        listLocations.forEach(l -> loc.add(
                new LocationFullProjection(l.getId(), l.getName(), l.getLat(), l.getLon(), l.getRadius(),
                        l.getDescription(), l.getType(), findEventsByLocId(l.getId(), EventSortType.UPCOMING)))
        );

        return new PageImpl<>(loc, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocationFullProjection> findByLocId(Long locId, EventSortType eventStatus) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);

        Predicate idPredicate = cb.equal(root.get("id"), locId);
        cq.select(root).where(idPredicate);

        try {
            Location singleResult = entityManager.createQuery(cq).getSingleResult();
            return Optional.of(new LocationFullProjection(
                    singleResult.getId(),
                    singleResult.getName(),
                    singleResult.getLat(),
                    singleResult.getLon(),
                    singleResult.getRadius(),
                    singleResult.getDescription(),
                    singleResult.getType(),
                    findEventsByLocId(locId, eventStatus)
            ));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocationFullProjection> searchLocations(PublicLocationService.SearchParams params) {
        int from = params.getFrom();
        int size = params.getSize();
        PageRequest pageable = PageRequest.of(from / size, size);
        Double radius = params.getRadius();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);

        Double lat = params.getLat();
        Double lon = params.getLon();
        Predicate distancePredicate;

        if (lat == null || lon == null || radius == null) {
            distancePredicate = cb.conjunction();
        } else {
            Expression<Double> distance = cb.function(
                    "distance",
                    Double.class,
                    cb.literal(lat),
                    cb.literal(lon),
                    root.get("lat"),
                    root.get("lon")
            );

            distancePredicate = cb.lessThanOrEqualTo(distance, radius);
        }

        String text = params.getText();
        Predicate texPredicate;

        if (text == null || text.isBlank()) {
            texPredicate = cb.conjunction();
        } else {
            texPredicate = cb.or(cb.like(cb.upper(root.get("name")), "%" + text.toUpperCase() + "%"),
                    cb.like(cb.upper(root.get("description")), "%" + text.toUpperCase() + "%"));
        }

        CriteriaQuery<Location> select = cq.select(root).where(cb.and(distancePredicate, texPredicate));

        TypedQuery<Location> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Location> listLocations = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Location> countRoot = countQuery.from(Location.class);
        countQuery.select(cb.count(countRoot));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        List<LocationFullProjection> loc = new ArrayList<>();

        listLocations.forEach(l -> loc.add(
                new LocationFullProjection(l.getId(), l.getName(), l.getLat(), l.getLon(), l.getRadius(),
                        l.getDescription(), l.getType(), findEventsByLocId(l.getId(), EventSortType.UPCOMING)))
        );

        return new PageImpl<>(loc, pageable, count);
    }

    private List<EventShortProjection> findEventsByLocId(Long locId, EventSortType eventStatus) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventShortProjection> cq = cb.createQuery(EventShortProjection.class);
        Root<Event> eventRoot = cq.from(Event.class);

        Predicate statusPredicate = cb.equal(eventRoot.get("state"), EventState.PUBLISHED);
        Predicate equal = cb.equal(eventRoot.get("location").get("id"), locId);
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
                .where(cb.and(statusPredicate, equal, eventDate));

        return entityManager.createQuery(cq).getResultList();
    }
}
