package ru.practicum.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.repository.projection.CountHitProjection;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
@SpringBootTest
class HitMapperTest {
    @Autowired
    private HitMapper hitMapper;

    @Test
    void toHitTest() {
        var test = hitMapper.toHit(RequestHitDto.builder()
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
    void toResponseStatsDto() {
        var test = hitMapper.toResponseStatsDto(
                new CountHitProjection(3L, "ewm-main-service", "/events/1"));

        assertNotNull(test);
        assertEquals("ewm-main-service", test.getApp());
        assertEquals("/events/1", test.getUri());
        assertEquals(3L, test.getHits());
    }
}