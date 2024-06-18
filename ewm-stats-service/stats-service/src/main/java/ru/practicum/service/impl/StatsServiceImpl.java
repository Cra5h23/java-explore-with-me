package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Stats;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final StatsMapper mapper;

    /**
     * @param dto
     */
    @Override
    public void saveStats(RequestStatsDto dto) {
        var stats = mapper.toStats(dto);

        Stats save = repository.save(stats);
        log.info("Сохранение статистики {}", save);
    }

    /**
     * @param params
     * @return
     */
    @Override
    public List<ResponseStatsDto> getStats(Params params) {
        log.info("Получение статистики с параметрами {}", params);

//        if (!params.isUnique()) {
//            if (params.getUris() != null) {
//                return repository.getCountByUris(params.getStart(), params.getEnd(), params.getUris())
//                        .stream()
//                        .map(mapper::toResponseStatsDto)
//                        .collect(Collectors.toList());
//            }
//
//            return repository.getCount(params.getStart(), params.getEnd())
//                    .stream()
//                    .map(mapper::toResponseStatsDto)
//                    .collect(Collectors.toList());
//        } else {
//            if (params.getUris() != null) {
//                return repository.getCountDistinctByUris(params.getStart(), params.getEnd(), params.getUris())
//                        .stream()
//                        .map(mapper::toResponseStatsDto)
//                        .collect(Collectors.toList());
//            }
//
//            return repository.getCountDistinct(params.getStart(), params.getEnd())
//                    .stream()
//                    .map(mapper::toResponseStatsDto)
//                    .collect(Collectors.toList());
//        }
        var start = toZoneDataTime(params.getStart());
        var end = toZoneDataTime(params.getEnd());

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

    private ZonedDateTime toZoneDataTime(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault());
    }
}
