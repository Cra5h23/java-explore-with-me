package ru.practicum.location.service;

import ru.practicum.location.dto.AdminLocationDtoResponse;
import ru.practicum.location.dto.LocationDtoRequest;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface AdminLocationService {
    AdminLocationDtoResponse addLocation(LocationDtoRequest locationDto);

    Object getLocation(Long locId);

    Object updateLocation(Long locId, LocationDtoRequest locationDto);

    void deleteLocation(Long locId);
}
