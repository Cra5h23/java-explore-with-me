package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.service.AdminCompilationService;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.dto.compilation.CompilationDtoResponse;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventService eventService;
    private final CompilationService compilationService;

    /**
     * @param compilation
     * @return
     */
    @Override
    @Transactional
    public CompilationDtoResponse addCompilation(NewCompilationDto compilation) {
        Set<Long> events = compilation.getEvents();
        List<EventShortDto> eventShortDtoList = null;
        if (events != null) {
            eventShortDtoList = eventService.getEvents(events);
        }
        Compilation c = compilationMapper.toCompilation(compilation);
        Compilation save = compilationRepository.save(c);

        return compilationMapper.toCompilationResponse(save, eventShortDtoList);
    }

    /**
     * @param compId
     */
    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = compilationService.checkCompilation(compId);
        compilationRepository.delete(compilation);
    }

    /**
     * @param compId
     * @param compilation
     * @return
     */
    @Override
    public CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequest compilation) {
        var compilationUpdated = compilationService.checkCompilation(compId);
        var title = compilation.getTitle();
        var events = compilation.getEvents();
        var pinned = compilation.getPinned();

        if (pinned != null) {
            compilationUpdated.setPinned(pinned);
        }

        if (title != null) {
            compilationUpdated.setTitle(title);
        }

        if (events != null) {
            compilationUpdated.setEvents(events.stream()
                    .map(id -> Event.builder().id(id).build())
                    .collect(Collectors.toSet()));
        }

        var compilationUpdate = compilationRepository.save(compilationUpdated);
        var eventSet = compilationUpdate.getEvents();
        var eventIds = compilationService.getEventIds(eventSet);
        var eventShortDtoList = eventService.getEvents(eventIds);

        return compilationMapper.toCompilationResponse(compilationUpdate, eventShortDtoList);
    }
}
