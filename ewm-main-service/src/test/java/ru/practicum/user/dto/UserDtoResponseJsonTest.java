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
 * @Date 04.07.2024
 */
@JsonTest
class UserDtoResponseJsonTest {
    @Autowired
    private JacksonTester<UserDtoResponse> json;

    @Test
    void testSerialize() throws IOException {
        var dto = UserDtoResponse.builder()
                .id(1L)
                .name("testName")
                .email("testEmail@test.com")
                .build();

        var result = json.write(dto);

        assertNotNull(result);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("testName");
        assertThat(result).hasJsonPath("$.email");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("testEmail@test.com");
    }

    @Test
    void testToObject() throws IOException {
        var dto = json.parseObject("{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"testName\",\n" +
                "  \"email\": \"testEmail@test.com\"\n" +
                "}");

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getName()).isEqualTo("testName");
        assertThat(dto.getEmail()).isEqualTo("testEmail@test.com");
        assertThat(dto.getClass()).isEqualTo(UserDtoResponse.class);
    }
}