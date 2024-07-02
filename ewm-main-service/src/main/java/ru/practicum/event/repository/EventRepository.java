package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    Page<EventShort> findAllByInitiatorId(Long initiatorId, Pageable page);

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

    interface EventShort {
        long getConfirmedRequests();

        String getTitle();

        String getAnnotation();

        Category getCategory();

        Long getId();

        ZonedDateTime getEventDate();

        User getInitiator();

        boolean getPaid();

        ZonedDateTime getPublishedOn();
    }
}
