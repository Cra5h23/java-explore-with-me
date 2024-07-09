package ru.practicum.location.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.location.dto.LocationDtoResponse;
import ru.practicum.location.model.AdminLocation;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface LocationRepository extends JpaRepository<AdminLocation, Long> {
}
