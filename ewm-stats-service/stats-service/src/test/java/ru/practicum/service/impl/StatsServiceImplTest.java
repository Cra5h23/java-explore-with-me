package ru.practicum.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
class StatsServiceImplTest {
    private StatsService statsService;
    private StatsRepository statsRepository;
    private StatsMapper statsMapper;

    @BeforeEach
    void setUp() {
        statsRepository = Mockito.mock(StatsRepository.class);
        statsMapper = new StatsMapper();

        statsService = new StatsServiceImpl(statsRepository, statsMapper);
    }

    @Test
    void saveStatsTest() {
        Mockito.when(statsRepository.save(Mockito.any(Stats.class)))
                .thenReturn(Stats.builder()
                        .id(1L)
                        .ip("192.163.0.1")
                        .app("ewm-main-service")
                        .uri("/events/1")
                        .timestamp(LocalDateTime.parse("2022-09-06 11:00:23",
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                .atZone(ZoneId.systemDefault())).build());

        statsService.saveStats(RequestStatsDto.builder()
                .uri("/events/1")
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:23")
                .build());

        Mockito.verify(statsRepository, Mockito.times(1))
                .save(Mockito.any(Stats.class));
    }

    @Test
    void getStatsTestValidUniqueFalseUrisNull() {
        Mockito.when(this.statsRepository.getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull()))
                .thenReturn(List.of(
                        new StatsRepository.CountStats() {
                            @Override
                            public Long getCount() {
                                return 9L;
                            }

                            @Override
                            public String getApp() {
                                return "ewm-main-service";
                            }

                            @Override
                            public String getUri() {
                                return "/events/1";
                            }
                        }, new StatsRepository.CountStats() {
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
                                return "/events/2";
                            }
                        }
                ));

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(null)
                .unique(false)
                .start("2021-01-01 20:20:20")
                .end("2025-01-01 20:20:20")
                .build());

        Assertions.assertNotNull(stats);
        Assertions.assertNotNull(stats.get(0));
        Assertions.assertEquals(9L, stats.get(0).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());
        Assertions.assertEquals(3L, stats.get(1).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(1).getApp());
        Assertions.assertEquals("/events/2", stats.get(1).getUri());

        Mockito.verify(statsRepository, Mockito.times(1))
                .getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull());
    }

    @Test
    void getStatsTestValidUniqueTrueUrisNull() {
        Mockito.when(this.statsRepository.getUniqueCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull()))
                .thenReturn(List.of(
                        new StatsRepository.CountStats() {
                            @Override
                            public Long getCount() {
                                return 9L;
                            }

                            @Override
                            public String getApp() {
                                return "ewm-main-service";
                            }

                            @Override
                            public String getUri() {
                                return "/events/1";
                            }
                        }, new StatsRepository.CountStats() {
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
                                return "/events/2";
                            }
                        }
                ));

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(null)
                .unique(true)
                .start("2021-01-01 20:20:20")
                .end("2025-01-01 20:20:20")
                .build());

        Assertions.assertNotNull(stats);
        Assertions.assertNotNull(stats.get(0));
        Assertions.assertEquals(9L, stats.get(0).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());
        Assertions.assertEquals(3L, stats.get(1).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(1).getApp());
        Assertions.assertEquals("/events/2", stats.get(1).getUri());

        Mockito.verify(statsRepository, Mockito.times(1))
                .getUniqueCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull());
    }

    @Test
    void getStatsTestValidUniqueTrueUrisExists() {
        Mockito.when(this.statsRepository.getUniqueCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.anyList()))
                .thenReturn(List.of(
                        new StatsRepository.CountStats() {
                            @Override
                            public Long getCount() {
                                return 9L;
                            }

                            @Override
                            public String getApp() {
                                return "ewm-main-service";
                            }

                            @Override
                            public String getUri() {
                                return "/events/1";
                            }
                        }, new StatsRepository.CountStats() {
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
                        }
                ));

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(List.of("/events/1"))
                .unique(true)
                .start("2021-01-01 20:20:20")
                .end("2025-01-01 20:20:20")
                .build());

        Assertions.assertNotNull(stats);
        Assertions.assertNotNull(stats.get(0));
        Assertions.assertEquals(9L, stats.get(0).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());
        Assertions.assertEquals(3L, stats.get(1).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());

        Mockito.verify(statsRepository, Mockito.times(1))
                .getUniqueCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.anyList());
    }

    @Test
    void getStatsTestValidUniqueFalseUrisExists() {
        Mockito.when(this.statsRepository.getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.anyList()))
                .thenReturn(List.of(
                        new StatsRepository.CountStats() {
                            @Override
                            public Long getCount() {
                                return 9L;
                            }

                            @Override
                            public String getApp() {
                                return "ewm-main-service";
                            }

                            @Override
                            public String getUri() {
                                return "/events/1";
                            }
                        }, new StatsRepository.CountStats() {
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
                        }
                ));

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(List.of("/events/1"))
                .unique(false)
                .start("2021-01-01 20:20:20")
                .end("2025-01-01 20:20:20")
                .build());

        Assertions.assertNotNull(stats);
        Assertions.assertNotNull(stats.get(0));
        Assertions.assertEquals(9L, stats.get(0).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());
        Assertions.assertEquals(3L, stats.get(1).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());

        Mockito.verify(statsRepository, Mockito.times(1))
                .getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.anyList());
    }
}