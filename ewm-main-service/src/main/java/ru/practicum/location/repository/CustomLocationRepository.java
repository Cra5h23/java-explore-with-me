package ru.practicum.location.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.location.dto.EventSortType;
import ru.practicum.location.repository.projection.LocationFullProjection;
import ru.practicum.location.service.PublicLocationService;

import java.util.Optional;

/**
 * @author Nikolay Radzivon
 * @Date 09.07.2024
 */
public interface CustomLocationRepository {
    Page<LocationFullProjection> findLocations(Pageable pageable);

    Optional<LocationFullProjection> findByLocId(Long locId, EventSortType eventStatus);

    Page<LocationFullProjection> searchLocations(PublicLocationService.SearchParams params);
}
