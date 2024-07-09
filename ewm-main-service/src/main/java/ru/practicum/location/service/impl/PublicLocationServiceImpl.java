package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.location.dto.LocationDtoResponse;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.AdminLocation;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.service.LocationService;
import ru.practicum.location.service.PublicLocationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PublicLocationServiceImpl implements PublicLocationService {
    private final LocationRepository locationRepository;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<LocationDtoResponse> getLocations(GetLocationsParams params) {
        if (params == null) {
            return List.of();
        }

        int from = params.getFrom();
        int size = params.getSize();

        PageRequest page = PageRequest.of(from / size, size);

        List<AdminLocation> collect = locationRepository.findAll(page).stream().collect(Collectors.toList());

        Map<Long, List<EventShortDto>> events = new HashMap<>();

        collect.forEach(l -> events.put(l.getId(), eventService.getEventsByLocation(l.getLat(), l.getLon(), l.getRadius())));

        return locationMapper.toListLocationDtoResponse(collect, events);
    }
}
