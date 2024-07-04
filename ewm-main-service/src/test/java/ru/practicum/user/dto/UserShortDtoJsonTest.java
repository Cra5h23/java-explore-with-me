package ru.practicum.user.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Nikolay Radzivon
 * @Date 05.07.2024
 */
@JsonTest
class UserShortDtoJsonTest {
    @Autowired
    JacksonTester<UserShortDto> json;

    @Test
    void testSerialize() throws IOException {
        var dto = UserShortDto.builder()
                .id(1L)
                .name("testName")
                .build();

        var result = json.write(dto);

        assertNotNull(result);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("testName");
    }

    @Test
    void testToObject() throws IOException {
        var dto = json.parseObject("{\"id\": 1, \"name\": \"testName\"}");

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getName()).isEqualTo("testName");
        assertThat(dto.getClass()).isEqualTo(UserShortDto.class);
    }
}