package ru.practicum.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDtoRequest {
    /**
     * Название категории.
     */
    @NotBlank(message = "Название категории не может быть пустым")
    @Size(min = 1, max = 50, message = "Название категории не может быть меньше {min} и больше {max} символов")
    private String name;
}
