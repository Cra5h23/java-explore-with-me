package ru.practicum.compilation.service;

import ru.practicum.dto.compilation.CompilationDtoResponse;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
public interface AdminCompilationService {
    CompilationDtoResponse addCompilation(NewCompilationDto compilation);

    void deleteCompilation(Long compId);

    CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequest compilation);
}
