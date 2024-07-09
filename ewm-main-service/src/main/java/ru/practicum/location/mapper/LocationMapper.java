package ru.practicum.location.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.location.dto.AdminLocationDtoResponse;
import ru.practicum.location.dto.LocationDtoRequest;
import ru.practicum.location.dto.LocationDtoResponse;
import ru.practicum.location.model.AdminLocation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Component
public class LocationMapper {
    public AdminLocation toLocation(LocationDtoRequest dto) {
        return AdminLocation.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .radius(dto.getRadius())
                .description(dto.getDescription())
                .name(dto.getName())
                .build();
    }

    public AdminLocationDtoResponse toAdminLocationDtoResponse(AdminLocation save) {
        return AdminLocationDtoResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .description(save.getDescription())
                .lat(save.getLat())
                .lon(save.getLon())
                .radius(save.getRadius())
                .build();
    }

    public List<LocationDtoResponse> toListLocationDtoResponse(List<AdminLocation> locations, Map<Long, List<EventShortDto>> events) {
        return locations.stream()
                .map(l -> LocationDtoResponse.builder()
                        .id(l.getId())
                        .lat(l.getLat())
                        .lon(l.getLon())
                        .radius(l.getRadius())
                        .name(l.getName())
                        .description(l.getDescription())
                        .events(events != null ? events.getOrDefault(l.getId(), List.of()) : List.of())
                        .build()
                ).collect(Collectors.toList());
    }
}
