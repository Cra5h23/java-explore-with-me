package ru.practicum.event.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author Nikolay Radzivon
 * @Date 28.06.2024
 */
@Getter
@Setter
@ToString
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
}
