package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.BadRequestLocationException;
import ru.practicum.exception.ConflictLocationException;
import ru.practicum.exception.NotFoundLocationException;
import ru.practicum.location.model.Location;
import ru.practicum.location.model.TypeLocation;
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
    public Location checkLocation(Long locId) {
        log.info("Проверка на то что локация с id {} существует", locId);
        return locationRepository.findById(locId)
                .orElseThrow(() -> new NotFoundLocationException(
                        String.format("Локация с id %d не существует или не доступна", locId)));
    }

    @Override
    @Transactional
    public Location addUserLocation(Float lon, Float lat) {
        log.info("Попытка добавить пользовательскую локацию с параметрами lon={}, lat={}", lon, lat);
        if (lon == null || lat == null) {
            throw new BadRequestLocationException(String.format(
                    "Нельзя добавить локацию параметрами lon=%s, lat=%s", lon, lat));
        }

        var build = Location.builder()
                .lat(lat)
                .lon(lon)
                .type(TypeLocation.USERS)
                .build();

        return locationRepository.save(build);
    }

    @Override
    @Transactional(readOnly = true)
    public Location checkAdminLocation(Long locId) {
        var location = checkLocation(locId);
        if (location.getType() != TypeLocation.ADMINS) {
            throw new NotFoundLocationException(String.format("Локация с id %d не найдена или не доступна", locId));
        }
        return location;
    }

    @Override
    @Transactional
    public Location updateUserLocation(Long locId, Float lon, Float lat) {
        log.info("Попытка обновить пользовательскую локацию с id {} новые данные lon={}, lat={}", locId, lon, lat);
        Location location = checkLocation(locId);
        log.info("Старые данные {}", location);

        if (location.getType() != TypeLocation.USERS) {
            throw new ConflictLocationException(String.format(
                    "Нельзя обновить локацию с id %d она не является пользовательской", locId));
        }

        if (lon != null) {
            location.setLon(lon);
        }

        if (lat != null) {
            location.setLat(lat);
        }

        return locationRepository.save(location);
    }
}
