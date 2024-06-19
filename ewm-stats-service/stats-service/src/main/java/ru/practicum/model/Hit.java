package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Класс сущности {@link Hit}, содержащий данные о посещении.
 *
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hits")
public class Hit {
    /**
     * Идентификационный номер посещения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор сервиса для которого записывается информация.
     */
    @Column(name = "app", nullable = false)
    private String app;

    /**
     * URI для которого был осуществлен запрос.
     */
    @Column(name = "uri", nullable = false)
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос.
     */
    @Column(name = "ip", length = 15, nullable = false)
    private String ip;

    /**
     * Дата и время посещения.
     */
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;
}
