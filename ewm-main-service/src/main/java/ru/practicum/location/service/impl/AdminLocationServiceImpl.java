package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.location.dto.AdminLocationDtoCreated;
import ru.practicum.location.dto.AdminLocationDtoRequest;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.TypeLocation;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.service.AdminLocationService;
import ru.practicum.location.service.LocationService;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminLocationServiceImpl implements AdminLocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationService locationService;

    @Override
    @Transactional
    public AdminLocationDtoCreated addLocation(AdminLocationDtoRequest locationDto) {
        var location = locationMapper.toLocation(locationDto, TypeLocation.ADMINS);
        var save = locationRepository.save(location);
        return locationMapper.toAdminLocationDtoResponse(save);
    }

    @Override
    @Transactional
    public AdminLocationDtoCreated updateLocation(Long locId, AdminLocationDtoRequest locationDto) {
        if (locationDto == null || locId == null) {
            return null;
        }

        var location = locationService.checkLocation(locId);

        var lon = locationDto.getLon();
        if (lon != null) {
            location.setLon(lon);
        }

        var lat = locationDto.getLat();
        if (lat != null) {
            location.setLat(lat);
        }

        if (location.getType() == TypeLocation.ADMINS) {
            var radius = locationDto.getRadius();
            if (radius != null) {
                location.setRadius(radius);
            }

            var description = locationDto.getDescription();
            if (description != null) {
                location.setDescription(description);
            }

            var name = locationDto.getName();
            if (name != null) {
                location.setName(name);
            }
        }
        var save = locationRepository.save(location);

        return locationMapper.toAdminLocationDtoResponse(save);
    }

    @Override
    @Transactional
    public void deleteLocation(Long locId) {
        var adminLocation = locationService.checkLocation(locId);
        locationRepository.delete(adminLocation);
    }
}
