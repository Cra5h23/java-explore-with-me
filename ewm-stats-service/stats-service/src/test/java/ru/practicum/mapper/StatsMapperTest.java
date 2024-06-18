package ru.practicum.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
class StatsMapperTest {
    private StatsMapper statsMapper;

    @BeforeEach
    void setUp() {
        statsMapper = new StatsMapper();
    }

    @Test
    void toStatsTest() {
        var test = statsMapper.toStats(RequestStatsDto.builder()
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:23")
                .app("ewm-main-service")
                .uri("/events/1")
                .build());

        var time = LocalDateTime.parse("2022-09-06 11:00:23",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault());

        assertNotNull(test);
        assertNull(test.getId());
        assertEquals("192.163.0.1", test.getIp());
        assertEquals("/events/1", test.getUri());
        assertEquals("ewm-main-service", test.getApp());
        assertEquals(time, test.getTimestamp());
    }

    @Test
    void toResponseStatsDto() throws InstantiationException, IllegalAccessException {
        var test = statsMapper.toResponseStatsDto(new StatsRepository.CountStats() {
            @Override
            public Long getCount() {
                return 3L;
            }

            @Override
            public String getApp() {
                return "ewm-main-service";
            }

            @Override
            public String getUri() {
                return "/events/1";
            }
        });

        assertNotNull(test);
        assertEquals("ewm-main-service", test.getApp());
        assertEquals("/events/1", test.getUri());
        assertEquals(3L, test.getHits());
    }
}