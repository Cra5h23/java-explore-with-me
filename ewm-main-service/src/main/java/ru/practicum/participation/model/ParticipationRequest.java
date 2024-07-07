package ru.practicum.participation.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.event.dto.EventRequestStatus;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "participation_requests")
@DynamicUpdate
public class ParticipationRequest {
    /**
     * Идентификационный номер запроса на участие.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата и время создания заявки.
     */
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    /**
     * Событие, к которому относится запрос.
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Event.class)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;

    /**
     * Пользователь, создавший запрос.
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "requester_id")
    @ToString.Exclude
    private User requester;

    /**
     * Статус запроса на участие.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventRequestStatus status;
}
