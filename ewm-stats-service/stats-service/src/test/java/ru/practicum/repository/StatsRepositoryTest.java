package ru.practicum.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.model.Hit;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
@DataJpaTest
class StatsRepositoryTest {
    @Autowired
    StatsRepository statsRepository;

    @BeforeEach
    void setUp() {
        statsRepository.save(Hit.builder()
                .uri("/events/1")
                .ip("192.163.0.1")
                .app("ewm-main-service")
                .timestamp(LocalDateTime.parse("2022-09-06 11:00:23",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(ZoneId.systemDefault()))
                .build());

        statsRepository.save(Hit.builder()
                .uri("/events/1")
                .ip("192.163.0.1")
                .app("ewm-main-service")
                .timestamp(LocalDateTime.parse("2022-09-08 11:00:23",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(ZoneId.systemDefault()))
                .build());

        statsRepository.save(Hit.builder()
                .uri("/events/1")
                .ip("192.163.0.2")
                .app("ewm-main-service")
                .timestamp(LocalDateTime.parse("2022-09-07 11:00:23",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(ZoneId.systemDefault()))
                .build());

        statsRepository.save(Hit.builder()
                .uri("/events/2")
                .ip("192.163.0.1")
                .app("ewm-main-service")
                .timestamp(LocalDateTime.parse("2022-09-07 11:00:23",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(ZoneId.systemDefault()))
                .build());

        statsRepository.save(Hit.builder()
                .uri("/events/3")
                .ip("192.163.0.1")
                .app("ewm-main-service")
                .timestamp(LocalDateTime.parse("2022-09-07 11:00:23",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(ZoneId.systemDefault()))
                .build());
    }

    @Test
    void getCountHitsTestUrisNull() {
        var test = statsRepository.getCountHit(
                StatsService.Params.builder()
                        .start(LocalDateTime.of(2020, 9, 6, 11, 0, 23))
                        .end(LocalDateTime.of(2025, 9, 6, 11, 0, 23))
                        .uris(null)
                        .unique(false)
                        .build());

        long count = statsRepository.count();
        assertEquals(5, count);
        assertNotNull(test);
        assertEquals(3, test.size());
        assertEquals(3, test.get(0).getCount());
        assertEquals("/events/1", test.get(0).getUri());
        assertEquals("ewm-main-service", test.get(0).getApp());
        assertEquals(1, test.get(1).getCount());
        assertEquals("/events/2", test.get(1).getUri());
        assertEquals("ewm-main-service", test.get(1).getApp());
        assertEquals(1, test.get(2).getCount());
        assertEquals("/events/3", test.get(2).getUri());
        assertEquals("ewm-main-service", test.get(2).getApp());
    }

    @Test
    void getCountHitsTestUrisExists() {
        var test = statsRepository.getCountHit(
                StatsService.Params.builder()
                        .start(LocalDateTime.of(2020, 9, 6, 11, 0, 23))
                        .end(LocalDateTime.of(2025, 9, 6, 11, 0, 23))
                        .uris(List.of("/events/1", "/events/2"))
                        .unique(false)
                        .build());

        long count = statsRepository.count();
        assertEquals(5, count);
        assertNotNull(test);
        assertEquals(2, test.size());
        assertEquals(3, test.get(0).getCount());
        assertEquals("/events/1", test.get(0).getUri());
        assertEquals("ewm-main-service", test.get(0).getApp());
        assertEquals(1, test.get(1).getCount());
        assertEquals("/events/2", test.get(1).getUri());
        assertEquals("ewm-main-service", test.get(1).getApp());
    }

    @Test
    void getUniqueCountHitsUrisNull() {
        var test = statsRepository.getCountHit(
                StatsService.Params.builder()
                        .start(LocalDateTime.of(2020, 9, 6, 11, 0, 23))
                        .end(LocalDateTime.of(2025, 9, 6, 11, 0, 23))
                        .uris(null)
                        .unique(true)
                        .build());

        long count = statsRepository.count();
        assertEquals(5, count);
        assertNotNull(test);
        assertEquals(3, test.size());
        assertEquals(2, test.get(0).getCount());
        assertEquals("/events/1", test.get(0).getUri());
        assertEquals("ewm-main-service", test.get(0).getApp());
        assertEquals(1, test.get(1).getCount());
        assertEquals("/events/2", test.get(1).getUri());
        assertEquals("ewm-main-service", test.get(1).getApp());
        assertEquals(1, test.get(2).getCount());
        assertEquals("/events/3", test.get(2).getUri());
        assertEquals("ewm-main-service", test.get(2).getApp());
    }

    @Test
    void getUniqueCountHitsUrisExists() {
        var test = statsRepository.getCountHit(
                StatsService.Params.builder()
                        .start(LocalDateTime.of(2020, 9, 6, 11, 0, 23))
                        .end(LocalDateTime.of(2025, 9, 6, 11, 0, 23))
                        .uris(List.of("/events/1", "/events/2"))
                        .unique(true)
                        .build());

        long count = statsRepository.count();
        assertEquals(5, count);
        assertNotNull(test);
        assertEquals(2, test.size());
        assertEquals(2, test.get(0).getCount());
        assertEquals("/events/1", test.get(0).getUri());
        assertEquals("ewm-main-service", test.get(0).getApp());
        assertEquals(1, test.get(1).getCount());
        assertEquals("/events/2", test.get(1).getUri());
        assertEquals("ewm-main-service", test.get(1).getApp());
    }
}