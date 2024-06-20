package ru.practicum.event.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.dto.compilation.CompilationDtoResponse;
import ru.practicum.event.service.PublicCompilationService;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {
    /**
     * @param pinned
     * @param from
     * @param size
     * @return
     */
    @Override
    public List<CompilationDtoResponse> getCompilations(Boolean pinned, Integer from, Integer size) {
        return List.of();
    }

    /**
     * @param compId
     * @return
     */
    @Override
    public CompilationDtoResponse getCompilation(Long compId) {
        return null;
    }
}
