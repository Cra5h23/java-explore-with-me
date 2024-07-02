package ru.practicum.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Dto для сущности подборка. Используется для получения данных подборки из запроса.
 *
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    /**
     * Список идентификационных номеров событий входящих в подборку.
     */
    private Set<Long> events;

    /**
     * Флаг закрепления подборки на главной странице сайта.
     */
    @Builder.Default
    private boolean pinned = false;

    /**
     * Заголовок подборки.
     */
    @NotBlank
    @Size(min = 1, max = 50, message = "Заголовок подборки не может быть меньше {min} и больше {max} символов")
    private String title;
}
