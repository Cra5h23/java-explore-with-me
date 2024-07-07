package ru.practicum.event.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventState;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
@DynamicUpdate
@Data
public class Event {
    /**
     * Идентификационный номер события.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Краткое описание события.
     */
    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;

    /**
     * Категория.
     */
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    /**
     * Полное описание события.
     */
    @Column(name = "description", nullable = false, length = 7000)
    private String description;

    /**
     * Дата и время на которое назначено событие.
     */
    @Column(name = "event_date", nullable = false)
    private ZonedDateTime eventDate;

    /**
     *
     */
    @OneToOne(targetEntity = Location.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    private Location location;

    /**
     * Флаг нужно ли оплачивать участие в событии.
     */
    @Column(name = "paid", nullable = false)
    private boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Column(name = "participant_limit", nullable = false)
    private int participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
     */
    @Column(name = "request_moderation", nullable = false)
    private boolean requestModeration;

    /**
     * Заголовок события.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Пользователь создатель события.
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User initiator;

    /**
     * Список состояний жизненного цикла события
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;

    /**
     * Дата и время создания события.
     */
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    /**
     * Дата и время публикации события.
     */
    @Column(name = "published_on")
    private ZonedDateTime publishedOn;

    /**
     * Количество подтверждённых заявок на участие
     */
    @Formula("(SELECT COUNT(*) FROM participation_requests pr WHERE pr.event_id = id and pr.status = 'CONFIRMED')")
    private long confirmedRequests;
}
