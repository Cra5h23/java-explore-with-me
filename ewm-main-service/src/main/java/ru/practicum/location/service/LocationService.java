package ru.practicum.location.service;

import ru.practicum.location.model.Location;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface LocationService {
    Location checkLocation(Long locId);

    Location addUserLocation(Float lon, Float lat);

    Location checkAdminLocation(Long id);

    Location updateUserLocation(Long id, Float lon, Float lat);
}
