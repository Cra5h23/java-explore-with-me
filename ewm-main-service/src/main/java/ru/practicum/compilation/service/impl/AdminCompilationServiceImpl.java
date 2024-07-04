package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.service.AdminCompilationService;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
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
    public CompilationDtoResponse addCompilation(NewCompilationDto compilation) {
        log.info("Попытка добавить новую подборку событий {}", compilation);
        var events = compilation.getEvents();
        List<EventShortDto> eventShortDtoList = null;

        if (events != null) {
            eventShortDtoList = eventService.getEvents(events);
        }

        var c = compilationMapper.toCompilation(compilation);
        var save = compilationRepository.save(c);
        log.info("Добавлена новая подборка событий {}", compilation);

        return compilationMapper.toCompilationResponse(save, eventShortDtoList);
    }

    /**
     * @param compId
     */
    @Override
    public void deleteCompilation(Long compId) {
        log.info("Попытка удалить подборку событий с id {}", compId);
        var compilation = compilationService.checkCompilation(compId);

        compilationRepository.delete(compilation);
        log.info("Удалена подборка событий {}", compilation);
    }

    /**
     * @param compId
     * @param compilation
     * @return
     */
    @Override
    public CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequest compilation) {
        log.info("Попытка обновить подборку событий с id {}", compId);
        var compilationUpdated = compilationService.checkCompilation(compId);
        var title = compilation.getTitle();
        var events = compilation.getEvents();
        var pinned = compilation.getPinned();

        log.info("Старые данные {}", compilationUpdated);
        log.info("Новые данные {}", compilation);

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

        log.info("Обновлена подборка событий {}", compilationUpdate);
        return compilationMapper.toCompilationResponse(compilationUpdate, eventShortDtoList);
    }
}
