package ru.practicum.compilation.service.impl;

import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.QCompilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.compilation.service.PublicCompilationService;
import ru.practicum.dto.compilation.CompilationDtoResponse;
import ru.practicum.event.service.EventService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;
    private final EventService eventService;

    /**
     * @param pinned
     * @param from
     * @param size
     * @return
     */
    @Override
    public List<CompilationDtoResponse> getCompilations(Boolean pinned, int from, int size) {
        var query = Expressions.asBoolean(true).isTrue();
        var page = PageRequest.of(from / size, size);
        Map<Long, List<Long>> compilationsEvents = new HashMap<>();

        if (pinned != null) {
            query = query.and(QCompilation.compilation.pinned.eq(pinned));
        }

        var collect = compilationRepository.findAll(query, page).stream()
                .peek(c -> compilationsEvents.put(c.getId(), compilationService.getEventIds(c.getEvents())))
                .collect(Collectors.toList());
        var events = eventService.getEvents(compilationsEvents);

        return compilationMapper.toListCompilationDtoResponse(collect, events);
    }

    /**
     * @param compId
     * @return
     */
    @Override
    public CompilationDtoResponse getCompilation(Long compId) {
        var compilation = compilationService.checkCompilation(compId);
        var eventIds = compilationService.getEventIds(compilation.getEvents());
        var events = eventService.getEvents(eventIds);

        return compilationMapper.toCompilationResponse(compilation, events);
    }
}
