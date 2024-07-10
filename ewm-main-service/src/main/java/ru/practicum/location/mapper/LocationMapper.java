package ru.practicum.location.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.location.dto.AdminLocationDtoCreated;
import ru.practicum.location.dto.AdminLocationDtoRequest;
import ru.practicum.location.dto.AdminLocationDtoResponse;
import ru.practicum.location.dto.UserLocationDtoResponse;
import ru.practicum.location.model.Location;
import ru.practicum.location.model.TypeLocation;
import ru.practicum.location.repository.projection.LocationFullProjection;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Component
public class LocationMapper {
    public Location toLocation(AdminLocationDtoRequest dto, TypeLocation typeLocation) {
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .radius(dto.getRadius())
                .description(dto.getDescription())
                .name(dto.getName())
                .type(typeLocation)
                .build();
    }

    public AdminLocationDtoCreated toAdminLocationDtoResponse(Location location) {
        return AdminLocationDtoCreated.builder()
                .id(location.getId())
                .name(location.getName())
                .description(location.getDescription())
                .lat(location.getLat())
                .lon(location.getLon())
                .radius(location.getRadius())
                .build();
    }

    public UserLocationDtoResponse toLocationDtoResponse(LocationFullProjection location, List<EventShortDto> events) {
        return location.getType() == TypeLocation.ADMINS ?
                AdminLocationDtoResponse.builder()
                        .lon(location.getLon())
                        .lat(location.getLat())
                        .id(location.getId())
                        .events(events)
                        .radius(location.getRadius())
                        .description(location.getDescription())
                        .name(location.getName())
                        .build() :
                UserLocationDtoResponse.builder()
                        .id(location.getId())
                        .lat(location.getLat())
                        .lon(location.getLon())
                        .events(events)
                        .build();
    }
}
