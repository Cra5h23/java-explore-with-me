package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;
}
