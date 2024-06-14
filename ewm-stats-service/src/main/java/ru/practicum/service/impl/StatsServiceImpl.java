package ru.practicum.service.impl;

import ru.practicum.dto.RequestStatsDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.service.StatsService;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public class StatsServiceImpl implements StatsService {
    /**
     * @param requestStatsDto
     */
    @Override
    public void addStats(RequestStatsDto requestStatsDto) {

    }

    /**
     * @param params
     * @return
     */
    @Override
    public List<ResponseStatsDto> getStats(Params params) {
        return List.of();
    }
}
