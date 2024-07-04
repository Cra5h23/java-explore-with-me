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
class NewUserRequestJsonTest {
    @Autowired
    private JacksonTester<NewUserRequest> json;

    @Test
    void testSerialize() throws IOException {
        var dto = NewUserRequest.builder()
                .name("testName")
                .email("testEmail@test.com")
                .build();

        var result = json.write(dto);

        assertNotNull(result);
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("testName");
        assertThat(result).hasJsonPath("$.email");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("testEmail@test.com");
    }

    @Test
    void testToObject() throws IOException {
        var dto = json.parseObject("{\"name\": \"testName\", \"email\": \"testEmail@test.com\"}");

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("testName");
        assertThat(dto.getEmail()).isEqualTo("testEmail@test.com");
        assertThat(dto.getClass()).isEqualTo(NewUserRequest.class);
    }
}