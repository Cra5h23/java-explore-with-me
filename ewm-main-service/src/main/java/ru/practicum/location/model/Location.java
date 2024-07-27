package ru.practicum.location.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 28.06.2024
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "locations")
@DynamicUpdate
public class Location {
    /**
     * Идентификационный номер локации проведения события.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Широта.
     */
    @Column(name = "lat")
    private float lat;

    /**
     * Долгота.
     */
    @Column(name = "lon")
    private float lon;

    @Column(name = "radius")
    private Float radius;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type", nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TypeLocation type = TypeLocation.USERS;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Event> events;
}
