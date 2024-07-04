package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto категории.
 *
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoResponse {
    /**
     * Идентификационный номер категории.
     */
    private Long id;

    /**
     * Название категории.
     */
    private String name;
}
