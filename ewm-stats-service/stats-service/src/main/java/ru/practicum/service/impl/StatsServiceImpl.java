package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatsService;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для сохранения и получения статистики посещений.
 * Реализует {@link StatsService}.
 *
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final HitMapper mapper;

    /**
     * Метод сохранения информацию о посещении в базу данных.
     *
     * @param dto {@link RequestHitDto} объект передачи данных посещения.
     */
    @Override
    @Transactional
    public void saveHit(RequestHitDto dto) {
        var stats = mapper.toHit(dto);

        Hit save = repository.save(stats);
        log.info("Сохранение посещения {}", save);
    }

    /**
     * Метод получения статистики посещений за определенный период времени.
     *
     * @param params параметры запроса статистики, включает дату начала и конца периода, а так же флаг уникальности.
     * @return список {@link ResponseStatsDto} статистика посещений.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseStatsDto> getStats(Params params) {
        log.info("Получение статистики с параметрами {}", params);

        var start = params.getStart().atZone(ZoneId.systemDefault());
        var end = params.getEnd().atZone(ZoneId.systemDefault());

        if (params.isUnique()) {
            return repository.getUniqueCountHits(start, end, params.getUris())
                    .stream()
                    .map(mapper::toResponseStatsDto)
                    .collect(Collectors.toList());
        } else {
            return repository.getCountHits(start, end, params.getUris())
                    .stream()
                    .map(mapper::toResponseStatsDto)
                    .collect(Collectors.toList());
        }
    }
}
