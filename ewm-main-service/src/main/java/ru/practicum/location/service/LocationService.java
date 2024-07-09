package ru.practicum.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.location.model.AdminLocation;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface LocationService {
    AdminLocation checkLocation(Long locId);
}
