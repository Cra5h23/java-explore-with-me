package ru.practicum.location.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.AdminLocationDtoResponse;
import ru.practicum.location.dto.LocationDtoRequest;
import ru.practicum.location.model.AdminLocation;

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
}
