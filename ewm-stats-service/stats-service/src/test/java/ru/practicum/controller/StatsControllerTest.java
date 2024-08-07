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
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.service.StatsService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void addHitTestValid() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"app\": \"ewm-main-service\",\n" +
                        "  \"uri\": \"/events/1\",\n" +
                        "  \"ip\": \"192.163.0.1\",\n" +
                        "  \"timestamp\": \"2022-09-06 11:00:23\"\n" +
                        "}");

        Mockito.doNothing().when(this.statsService).saveHit(Mockito.any(RequestHitDto.class));

        this.mockMvc.perform(request).andExpectAll(
                status().isCreated()
        );

        Mockito.verify(this.statsService, Mockito.times(1))
                .saveHit(Mockito.any(RequestHitDto.class));
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

    @Test
    void getStatsTestNotValidStartNotExists() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("end", "2024-01-20 20:20:20")
                .param("unique", "true");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'params' on field 'start': rejected value [null]; codes [NotNull.params.start,NotNull.start,NotNull.java.time.LocalDateTime,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [params.start,start]; arguments []; default message [start]]; default message [Параметр start должен быть указан]"),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void getStatsTestNotValidEndNotExists() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("start", "2024-01-20 20:20:20")
                .param("unique", "true");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'params' on field 'end': rejected value [null]; codes [NotNull.params.end,NotNull.end,NotNull.java.time.LocalDateTime,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [params.end,end]; arguments []; default message [end]]; default message [Параметр end должен быть указан]"),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void getStatsTestNotValidStartIsBad() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("start", "asddad")
                .param("end", "2024-01-20 20:20:20")
                .param("unique", "true");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'params' on field 'start': rejected value [asddad]; codes [typeMismatch.params.start,typeMismatch.start,typeMismatch.java.time.LocalDateTime,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [params.start,start]; arguments []; default message [start]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDateTime' for property 'start'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [@javax.validation.constraints.NotNull java.time.LocalDateTime] for value 'asddad'; nested exception is java.time.format.DateTimeParseException: Text 'asddad' could not be parsed at index 0]"),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void getStatsTestNotValidEndIsBad() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/stats")
                .param("start", "2024-01-20 20:20:20")
                .param("end", "ad12")
                .param("unique", "true");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'params' on field 'end': rejected value [ad12]; codes [typeMismatch.params.end,typeMismatch.end,typeMismatch.java.time.LocalDateTime,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [params.end,end]; arguments []; default message [end]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDateTime' for property 'end'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [@javax.validation.constraints.NotNull java.time.LocalDateTime] for value 'ad12'; nested exception is java.time.format.DateTimeParseException: Text 'ad12' could not be parsed at index 0]"),
                jsonPath("timestamp").exists()
        );
    }
}