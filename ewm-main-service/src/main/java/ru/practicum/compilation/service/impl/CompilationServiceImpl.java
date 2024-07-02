package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.model.Event;
import ru.practicum.exception.NotFoundCompilationException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public Compilation checkCompilation(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundCompilationException(
                        String.format("Подборка событий с id %d не найдена или не доступна", compId)));
    }

    @Override
    public List<Long> getEventIds(Collection<Event> events) {
        if (events == null || events.isEmpty()) {
            return List.of();
        }

        return events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
    }


}
