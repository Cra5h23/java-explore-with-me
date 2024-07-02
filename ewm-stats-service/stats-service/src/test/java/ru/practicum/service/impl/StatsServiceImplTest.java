package ru.practicum.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
@SpringBootTest
class StatsServiceImplTest {
    @Mock
    private StatsRepository statsRepository;

    @Mock
    private HitMapper hitMapper;

    @InjectMocks
    private StatsServiceImpl statsService;

    @Test
    void saveHitTest() {
        Mockito.when(hitMapper.toHit(Mockito.any(RequestHitDto.class)))
                .thenReturn(Hit.builder()
                        .ip("192.163.0.1")
                        .app("ewm-main-service")
                        .uri("/events/1")
                        .timestamp(ZonedDateTime.of(
                                2022, 9, 6, 11, 0, 23, 0, ZoneId.systemDefault()))
                        .build());
        Mockito.when(statsRepository.save(Mockito.any(Hit.class)))
                .thenReturn(Hit.builder()
                        .id(1L)
                        .ip("192.163.0.1")
                        .app("ewm-main-service")
                        .uri("/events/1")
                        .timestamp(ZonedDateTime.of(
                                2022, 9, 6, 11, 0, 23, 0, ZoneId.systemDefault()))
                        .build());

        statsService.saveHit(RequestHitDto.builder()
                .uri("/events/1")
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:23")
                .build());

        Mockito.verify(statsRepository, Mockito.times(1))
                .save(Mockito.any(Hit.class));
        Mockito.verify(hitMapper, Mockito.times(1))
                .toHit(Mockito.any(RequestHitDto.class));
    }

    @Test
    void getStatsTestValidUniqueFalseUrisNull() {
        Mockito.when(this.statsRepository.getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull()))
                .thenReturn(List.of(
                        new StatsRepository.CountHits() {
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
                        }, new StatsRepository.CountHits() {
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

        Mockito.when(hitMapper.toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class)))
                .thenAnswer(invocation -> {
                    StatsRepository.CountHits argument = invocation.getArgument(0);
                    ResponseStatsDto dto = new ResponseStatsDto();
                    dto.setHits(argument.getCount());
                    dto.setApp(argument.getApp());
                    dto.setUri(argument.getUri());
                    return dto;
                });

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(null)
                .unique(false)
                .start(LocalDateTime.of(2021, 1, 1, 20, 20, 20))
                .end(LocalDateTime.of(2025, 1, 1, 20, 20, 20))
                .build());

        Assertions.assertNotNull(stats);
        Assertions.assertEquals(2, stats.size()); // Проверка размера списка
        Assertions.assertEquals(9L, stats.get(0).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(0).getApp());
        Assertions.assertEquals("/events/1", stats.get(0).getUri());
        Assertions.assertEquals(3L, stats.get(1).getHits());
        Assertions.assertEquals("ewm-main-service", stats.get(1).getApp());
        Assertions.assertEquals("/events/2", stats.get(1).getUri());

        // Проверка вызовов моков
        Mockito.verify(statsRepository, Mockito.times(1))
                .getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull());

        Mockito.verify(hitMapper, Mockito.times(2))
                .toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class));
    }


    @Test
    void getStatsTestValidUniqueTrueUrisNull() {
        Mockito.when(this.statsRepository.getUniqueCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.isNull()))
                .thenReturn(List.of(
                        new StatsRepository.CountHits() {
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
                        }, new StatsRepository.CountHits() {
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

        Mockito.when(hitMapper.toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class)))
                .thenAnswer(invocation -> {
                    StatsRepository.CountHits argument = invocation.getArgument(0);
                    ResponseStatsDto dto = new ResponseStatsDto();
                    dto.setHits(argument.getCount());
                    dto.setApp(argument.getApp());
                    dto.setUri(argument.getUri());
                    return dto;
                });

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(null)
                .unique(true)
                .start(LocalDateTime.of(2021, 1, 1, 20, 20, 20))
                .end(LocalDateTime.of(2025, 1, 1, 20, 20, 20))
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
        Mockito.verify(hitMapper, Mockito.times(2))
                .toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class));
    }

    @Test
    void getStatsTestValidUniqueTrueUrisExists() {
        Mockito.when(this.statsRepository.getUniqueCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.anyList()))
                .thenReturn(List.of(
                        new StatsRepository.CountHits() {
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
                        }, new StatsRepository.CountHits() {
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

        Mockito.when(hitMapper.toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class)))
                .thenAnswer(invocation -> {
                    StatsRepository.CountHits argument = invocation.getArgument(0);
                    ResponseStatsDto dto = new ResponseStatsDto();
                    dto.setHits(argument.getCount());
                    dto.setApp(argument.getApp());
                    dto.setUri(argument.getUri());
                    return dto;
                });

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(List.of("/events/1"))
                .unique(true)
                .start(LocalDateTime.of(2021, 1, 1, 20, 20, 20))
                .end(LocalDateTime.of(2025, 1, 1, 20, 20, 20))
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

        Mockito.verify(hitMapper, Mockito.times(2))
                .toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class));
    }

    @Test
    void getStatsTestValidUniqueFalseUrisExists() {
        Mockito.when(this.statsRepository.getCountHits(Mockito.any(ZonedDateTime.class),
                        Mockito.any(ZonedDateTime.class), Mockito.anyList()))
                .thenReturn(List.of(
                        new StatsRepository.CountHits() {
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
                        }, new StatsRepository.CountHits() {
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

        Mockito.when(hitMapper.toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class)))
                .thenAnswer(invocation -> {
                    StatsRepository.CountHits argument = invocation.getArgument(0);
                    ResponseStatsDto dto = new ResponseStatsDto();
                    dto.setHits(argument.getCount());
                    dto.setApp(argument.getApp());
                    dto.setUri(argument.getUri());
                    return dto;
                });

        List<ResponseStatsDto> stats = statsService.getStats(StatsService.Params.builder()
                .uris(List.of("/events/1"))
                .unique(false)
                .start(LocalDateTime.of(2021, 1, 1, 20, 20, 20))
                .end(LocalDateTime.of(2025, 1, 1, 20, 20, 20))
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

        Mockito.verify(hitMapper, Mockito.times(2))
                .toResponseStatsDto(Mockito.any(StatsRepository.CountHits.class));
    }
}