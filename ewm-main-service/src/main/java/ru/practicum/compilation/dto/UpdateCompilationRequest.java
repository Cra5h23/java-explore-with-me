package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Dto для сущности подборки событий. Используется для получения новых данных о подборке событий для обновления.
 *
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequest {
    /**
     * Список идентификационных номеров событий. Для полной замены предыдущего.
     */
    private Set<Long> events;

    /**
     * Флаг закреплена ли подборка на главной странице сайта.
     */
    private Boolean pinned;

    /**
     * Новый заголовок подборки.
     */
    @Size(min = 1, max = 50, message = "Заголовок подборки не может быть меньше {min} и больше {max} символов")
    private String title;
}
