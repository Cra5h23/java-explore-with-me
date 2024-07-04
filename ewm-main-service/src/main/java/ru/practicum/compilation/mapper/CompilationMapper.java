package ru.practicum.compilation.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
@Component
public class CompilationMapper {

    public Compilation toCompilation(NewCompilationDto compilation) {
        return Compilation.builder()
                .events(compilation.getEvents() != null ? compilation.getEvents().stream()
                        .map(id -> Event.builder().id(id).build())
                        .collect(Collectors.toSet()) : null)
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    public CompilationDtoResponse toCompilationResponse(Compilation compilation, List<EventShortDto> eventShortDtoList) {
        return CompilationDtoResponse.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(eventShortDtoList)
                .build();
    }

    public List<CompilationDtoResponse> toListCompilationDtoResponse(List<Compilation> compilations,
                                                                     Map<Long, List<EventShortDto>> events) {
        return compilations.stream()
                .map(c -> toCompilationResponse(c, events.get(c.getId())))
                .collect(Collectors.toList());
    }
}