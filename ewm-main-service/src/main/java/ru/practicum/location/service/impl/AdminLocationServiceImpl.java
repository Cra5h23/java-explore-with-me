package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.AdminLocationDtoResponse;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.location.dto.LocationDtoRequest;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.AdminLocation;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.service.AdminLocationService;

import javax.validation.constraints.Null;
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

    @Override
    @Transactional
    public AdminLocationDtoResponse addLocation(LocationDtoRequest locationDto) {
        AdminLocation location = locationMapper.toLocation(locationDto);
        AdminLocation save = locationRepository.save(location);
        return locationMapper.toAdminLocationDtoResponse(save);
    }

    @Override
    public Object getLocation(Long locId) {
        Optional<AdminLocation> byId = locationRepository.findById(locId);


            AdminLocation adminLocation = byId.get();


        List<Event> eventsByLocation = eventService.getEventsByLocation(adminLocation.getLat(), adminLocation.getLon(), adminLocation.getRadius());


        System.out.println(eventsByLocation);

        return null;
    }
}
