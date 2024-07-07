package ru.practicum.compilation.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilations")
@DynamicUpdate
public class Compilation {
    /**
     * Идентификационный номер подборки событий
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Список событий входящих в подборку.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compilations_events", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ToString.Exclude
    private Set<Event> events;

    /**
     * Флаг закрепления на главной странице.
     */
    @Column(name = "pinned", nullable = false)
    @Builder.Default
    private boolean pinned = false;

    /**
     * Заголовок подборки
     */
    @Column(name = "title", length = 50, nullable = false)
    private String title;
}
