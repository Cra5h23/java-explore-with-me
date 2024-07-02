package ru.practicum.compilation.service;

import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.model.Event;

import java.util.Collection;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
public interface CompilationService {
    Compilation checkCompilation(Long compId);

    List<Long> getEventIds(Collection<Event> events);
}
