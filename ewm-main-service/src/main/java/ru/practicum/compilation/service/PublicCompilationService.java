package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDtoResponse;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
public interface PublicCompilationService {
    List<CompilationDtoResponse> getCompilations(Boolean pinned, int from, int size);

    CompilationDtoResponse getCompilation(Long compId);
}
