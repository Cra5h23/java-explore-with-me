package ru.practicum.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.service.StatsService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
@WebMvcTest(controllers = StatsController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @Test
    void addStatsTestValid() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"app\": \"ewm-main-service\",\n" +
                        "  \"uri\": \"/events/1\",\n" +
                        "  \"ip\": \"192.163.0.1\",\n" +
                        "  \"timestamp\": \"2022-09-06 11:00:23\"\n" +
                        "}");

        Mockito.doNothing().when(this.statsService).saveStats(Mockito.any(RequestStatsDto.class));

        this.mockMvc.perform(request).andExpectAll(
                status().isCreated()
        );

        Mockito.verify(this.statsService, Mockito.times(1))
                .saveStats(Mockito.any(RequestStatsDto.class));
    }

    @Test
    void getStatsTestValid() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("start", "2024-01-20 20:20:20")
                .param("end", "2024-01-20 20:20:20");

        Mockito.when(this.statsService.getStats(Mockito.any(StatsService.Params.class)))
                .thenReturn(List.of(ResponseStatsDto.builder()
                        .uri("ewm-main-service")
                        .hits(6)
                        .app("/events/1")
                        .build(), ResponseStatsDto.builder()
                        .uri("ewm-main-service")
                        .hits(2)
                        .app("/events/3")
                        .build()));

        this.mockMvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("[\n" +
                        "  {\n" +
                        "    \"app\": \"/events/1\",\n" +
                        "    \"uri\": \"ewm-main-service\",\n" +
                        "    \"hits\": 6\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"app\": \"/events/3\",\n" +
                        "    \"uri\": \"ewm-main-service\",\n" +
                        "    \"hits\": 2\n" +
                        "  }\n" +
                        "]")
        );

        Mockito.verify(this.statsService, Mockito.times(1))
                .getStats(Mockito.any(StatsService.Params.class));
    }

    @Test
    void getStatsTestValidUniqueTrue() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("start", "2024-01-20 20:20:20")
                .param("end", "2024-01-20 20:20:20")
                .param("unique", "true");

        Mockito.when(this.statsService.getStats(Mockito.any(StatsService.Params.class)))
                .thenReturn(List.of(ResponseStatsDto.builder()
                        .uri("ewm-main-service")
                        .hits(3)
                        .app("/events/1")
                        .build(), ResponseStatsDto.builder()
                        .uri("ewm-main-service")
                        .hits(2)
                        .app("/events/3")
                        .build()));

        this.mockMvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("[\n" +
                        "  {\n" +
                        "    \"app\": \"/events/1\",\n" +
                        "    \"uri\": \"ewm-main-service\",\n" +
                        "    \"hits\": 3\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"app\": \"/events/3\",\n" +
                        "    \"uri\": \"ewm-main-service\",\n" +
                        "    \"hits\": 2\n" +
                        "  }\n" +
                        "]")
        );

        Mockito.verify(this.statsService, Mockito.times(1))
                .getStats(Mockito.any(StatsService.Params.class));
    }

    @Test
    void getStatsTestValidUrisExists() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("start", "2024-01-20 20:20:20")
                .param("end", "2024-01-20 20:20:20")
                .param("uris", "/events/1");

        Mockito.when(this.statsService.getStats(Mockito.any(StatsService.Params.class)))
                .thenReturn(List.of(ResponseStatsDto.builder()
                        .uri("ewm-main-service")
                        .hits(6)
                        .app("/events/1")
                        .build(), ResponseStatsDto.builder()
                        .uri("ewm-main-service")
                        .hits(2)
                        .app("/events/1")
                        .build()));

        this.mockMvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("[\n" +
                        "  {\n" +
                        "    \"app\": \"/events/1\",\n" +
                        "    \"uri\": \"ewm-main-service\",\n" +
                        "    \"hits\": 6\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"app\": \"/events/1\",\n" +
                        "    \"uri\": \"ewm-main-service\",\n" +
                        "    \"hits\": 2\n" +
                        "  }\n" +
                        "]")
        );

        Mockito.verify(this.statsService, Mockito.times(1))
                .getStats(Mockito.any(StatsService.Params.class));
    }
}