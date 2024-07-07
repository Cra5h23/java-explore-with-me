package ru.practicum.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
@DynamicUpdate
public class Category {
    /**
     * Идентификационный номер категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название категории.
     */
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;
}
