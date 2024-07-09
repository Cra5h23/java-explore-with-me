package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.location.dto.AdminLocationDtoResponse;
import ru.practicum.location.dto.LocationDtoRequest;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.AdminLocation;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.service.AdminLocationService;
import ru.practicum.location.service.LocationService;

import java.util.List;
import java.util.Optional;

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
    private final EventService eventService;
    private final LocationService locationService;

    @Override
    @Transactional
    public AdminLocationDtoResponse addLocation(LocationDtoRequest locationDto) {
        AdminLocation location = locationMapper.toLocation(locationDto);
        AdminLocation save = locationRepository.save(location);
        return locationMapper.toAdminLocationDtoResponse(save);
    }

    @Override
    public Object getLocation(Long locId) {// todo delete method
        Optional<AdminLocation> byId = locationRepository.findById(locId);


        AdminLocation adminLocation = byId.get();


        List<EventShortDto> eventsByLocation = eventService.getEventsByLocation(adminLocation.getLat(), adminLocation.getLon(), adminLocation.getRadius());


        System.out.println(eventsByLocation);

        return null;
    }

    @Override
    @Transactional
    public AdminLocationDtoResponse updateLocation(Long locId, LocationDtoRequest locationDto) {
        if (locationDto == null || locId == null) {
            return null;
        }

        AdminLocation location = locationService.checkLocation(locId);

        Float lon = locationDto.getLon();
        if (lon != null) {
            location.setLon(lon);
        }

        Float lat = locationDto.getLat();
        if (lat != null) {
            location.setLat(lat);
        }

        Float radius = locationDto.getRadius();
        if (radius != null) {
            location.setRadius(radius);
        }

        String description = locationDto.getDescription();
        if (description != null) {
            location.setDescription(description);
        }

        String name = locationDto.getName();

        if (name != null) {
            location.setName(name);
        }

        AdminLocation save = locationRepository.save(location);

        return locationMapper.toAdminLocationDtoResponse(save);
    }

    @Override
    @Transactional
    public void deleteLocation(Long locId) {
        AdminLocation adminLocation = locationService.checkLocation(locId);
        locationRepository.delete(adminLocation);
    }
}
