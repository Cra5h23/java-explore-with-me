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
class ResponseStatsDtoTest {
    @Autowired
    private JacksonTester<ResponseStatsDto> json;

    @Test
    void testSerialize() throws IOException {
        var dto = ResponseStatsDto.builder()
                .hits(5)
                .app("ewm-main-service")
                .uri("/events/1")
                .build();

        var result = json.write(dto);

        assertNotNull(result);
        assertThat(result).hasJsonPath("$.hits");
        assertThat(result).extractingJsonPathNumberValue("$.hits").isEqualTo(5);
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo("ewm-main-service");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo("/events/1");
    }

    @Test
    void testToObject() throws IOException {
        var dto = json.parseObject("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/1\",\n" +
                "  \"hits\": 5\n" +
                "}");

        assertThat(dto).isNotNull();
        assertThat(dto.getApp()).isEqualTo("ewm-main-service");
        assertThat(dto.getHits()).isEqualTo(5);
        assertThat(dto.getUri()).isEqualTo("/events/1");
        assertThat(dto.getClass()).isEqualTo(ResponseStatsDto.class);
    }

}