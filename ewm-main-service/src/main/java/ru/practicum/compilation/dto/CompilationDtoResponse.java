package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.dto.event.EventShortDto;

import java.util.List;

/**
 * Dto подборки событий. Используется в качестве ответа.
 *
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDtoResponse {
    /**
     * Список событий входящих в подборку.
     */
    private List<EventShortDto> events;

    /**
     * Идентификационный номер подборки.
     */
    private Long id;

    /**
     * Флаг закрепления подборки на главной странице сайта. (true - закреплена, false - нет).
     */
    private boolean pinned;

    /**
     * Заголовок подборки.
     */
    private String title;
}
