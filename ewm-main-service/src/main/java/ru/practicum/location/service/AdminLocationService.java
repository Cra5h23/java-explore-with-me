package ru.practicum.location.service;

import ru.practicum.location.dto.AdminLocationDtoCreated;
import ru.practicum.location.dto.AdminLocationDtoRequest;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface AdminLocationService {
    AdminLocationDtoCreated addLocation(AdminLocationDtoRequest locationDto);

    AdminLocationDtoCreated updateLocation(Long locId, AdminLocationDtoRequest locationDto);

    void deleteLocation(Long locId);
}
