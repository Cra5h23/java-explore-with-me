package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Класс сущности {@link Hit}, содержащий данные о посещении.
 *
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Data
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hit)) return false;
        return id != null && id.equals(((Hit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
