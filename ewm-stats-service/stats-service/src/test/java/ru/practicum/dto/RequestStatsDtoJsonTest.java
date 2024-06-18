package ru.practicum.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
@JsonTest
class RequestStatsDtoJsonTest {
    @Autowired
    private JacksonTester<RequestStatsDto> json;

    @Test
    void testSerialize() throws IOException {
        var dto = RequestStatsDto.builder()
                .ip("192.163.0.1")
                .app("ewm-main-service")
                .timestamp("2022-09-06 11:00:23")
                .uri("/events/1")
                .build();

        var result = json.write(dto);

        assertNotNull(result);
        assertThat(result).hasJsonPath("$.ip");
        assertThat(result).extractingJsonPathStringValue("$.ip").isEqualTo("192.163.0.1");
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo("ewm-main-service");
        assertThat(result).hasJsonPath("$.timestamp");
        assertThat(result).extractingJsonPathStringValue("$.timestamp").isEqualTo("2022-09-06 11:00:23");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo("/events/1");
    }

    @Test
    void testToObject() throws IOException {
        var dto = json.parseObject("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/1\",\n" +
                "  \"ip\": \"192.163.0.1\",\n" +
                "  \"timestamp\": \"2022-09-06 11:00:23\"\n" +
                "}");

        assertThat(dto).isNotNull();
        assertThat(dto.getApp()).isEqualTo("ewm-main-service");
        assertThat(dto.getIp()).isEqualTo("192.163.0.1");
        assertThat(dto.getTimestamp()).isEqualTo("2022-09-06 11:00:23");
        assertThat(dto.getUri()).isEqualTo("/events/1");

        assertThat(dto.getClass()).isEqualTo(RequestStatsDto.class);
    }

}