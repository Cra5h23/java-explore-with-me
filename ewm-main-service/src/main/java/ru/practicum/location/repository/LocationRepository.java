package ru.practicum.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.location.model.Location;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface LocationRepository extends JpaRepository<Location, Long>, CustomLocationRepository {
}
