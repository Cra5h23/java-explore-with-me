package ru.practicum.location.model;

import lombok.*;
import org.hibernate.annotations.Formula;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_locations")
public class AdminLocation {
    /**
     * Идентификационный номер локации.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    @Column(name = "lon")
    private Float lon;
    @Column(name ="lat")
    private Float lat;

    @Column(name = "radius")
    private Float radius;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
