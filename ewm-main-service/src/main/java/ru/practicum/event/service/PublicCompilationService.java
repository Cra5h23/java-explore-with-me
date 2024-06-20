package ru.practicum.event.service;

import ru.practicum.dto.compilation.CompilationDtoResponse;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
public interface PublicCompilationService {
    List<CompilationDtoResponse> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDtoResponse getCompilation(Long compId);
}
