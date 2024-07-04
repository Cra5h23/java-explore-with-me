package ru.practicum.repository;

import ru.practicum.repository.projection.CountHitProjection;
import ru.practicum.service.StatsService;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 04.07.2024
 */
public interface CustomStatsRepository {
    List<CountHitProjection> getCountHit(StatsService.Params params);
}
