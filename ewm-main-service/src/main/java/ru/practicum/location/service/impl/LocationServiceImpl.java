package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundLocationException;
import ru.practicum.location.model.AdminLocation;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.service.LocationService;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminLocation checkLocation(Long locId) {
        return locationRepository.findById(locId)
                .orElseThrow(() -> new NotFoundLocationException(String.format("Локация с id %d не существует или не доступна", locId)));
    }
}
