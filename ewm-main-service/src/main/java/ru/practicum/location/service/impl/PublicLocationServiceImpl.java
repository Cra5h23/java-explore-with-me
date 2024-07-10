package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.NotFoundLocationException;
import ru.practicum.location.dto.EventSortType;
import ru.practicum.location.dto.UserLocationDtoResponse;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.service.PublicLocationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicLocationServiceImpl implements PublicLocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<? extends UserLocationDtoResponse> getLocations(GetLocationsParams params) {
        log.info("Запрос списка локаций с параметрами {}", params);
        if (params == null) {
            return List.of();
        }

        var from = params.getFrom();
        var size = params.getSize();

        var page = PageRequest.of(from / size, size);

        return locationRepository.findLocations(page).stream()
                .map(l -> locationMapper.toLocationDtoResponse(
                        l, eventService.getListEventShortDto(l.getEvents())))
                .collect(Collectors.toList());
    }

    @Override
    public UserLocationDtoResponse getLocation(Long locId, EventSortType eventStatus) {
        log.info("Запрос локации с id {} и списком событий {} для неё", locId, eventStatus.toString());
        var locationFullProjection = locationRepository.findByLocId(locId, eventStatus)
                .orElseThrow(() -> new NotFoundLocationException(
                        String.format("Локация с id %d не существует или не доступна", locId)));

        return locationMapper.toLocationDtoResponse(
                locationFullProjection, eventService.getListEventShortDto(locationFullProjection.getEvents()));
    }

    @Override
    public List<UserLocationDtoResponse> searchLocations(SearchParams params) {
        log.info("Пойск локаций с параметрами {}", params);
        return locationRepository.searchLocations(params).stream()
                .map(l -> locationMapper.toLocationDtoResponse(
                        l, eventService.getListEventShortDto(l.getEvents())))
                .collect(Collectors.toList());
    }
}
