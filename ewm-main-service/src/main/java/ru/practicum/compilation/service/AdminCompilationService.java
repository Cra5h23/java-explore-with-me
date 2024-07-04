package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
public interface AdminCompilationService {
    CompilationDtoResponse addCompilation(NewCompilationDto compilation);

    void deleteCompilation(Long compId);

    CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequest compilation);
}
