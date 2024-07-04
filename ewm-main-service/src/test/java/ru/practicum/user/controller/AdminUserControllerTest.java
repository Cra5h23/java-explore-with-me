package ru.practicum.user.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.exception.NotFoundUserException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.service.AdminUserService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@WebMvcTest(controllers = AdminUserController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AdminUserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AdminUserService adminUserService;

    @Test
    void getUsersTestValidIds1() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/admin/users")
                .param("ids", "1")
                .param("from", "0")
                .param("size", "10");

        Mockito.when(adminUserService.getUsers(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(UserDtoResponse.builder()
                        .id(1L)
                        .name("TestUser")
                        .email("testUser@email.test")
                        .build()));

        this.mockMvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("[{\"email\":\"testUser@email.test\",\"id\":1,\"name\":\"TestUser\"}]")
        );

        Mockito.verify(adminUserService, Mockito.times(1))
                .getUsers(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void getUsersTestValidIdsNotExists() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/admin/users")
                .param("from", "0")
                .param("size", "10");

        Mockito.when(adminUserService.getUsers(Mockito.isNull(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(UserDtoResponse.builder()
                        .id(1L)
                        .name("TestUser1")
                        .email("testUser1@email.test")
                        .build(), UserDtoResponse.builder()
                        .id(2L)
                        .name("TestUser2")
                        .email("testUser2@email.test")
                        .build()));

        this.mockMvc.perform(request).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("[{\"email\":\"testUser1@email.test\",\"id\":1,\"name\":\"TestUser1\"}," +
                        "{\"email\":\"testUser2@email.test\",\"id\":2,\"name\":\"TestUser2\"}]")
        );

        Mockito.verify(adminUserService, Mockito.times(1))
                .getUsers(Mockito.isNull(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void getUsersTestNotValidIdsBad() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/admin/users")
                .param("ids", "ad")
                .param("from", "0")
                .param("size", "10");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("Failed to convert value of type 'java.lang.String' to " +
                        "required type 'java.util.List'; nested exception is java.lang.NumberFormatException: For input string: \"ad\""),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void getUsersTestNotValidFromBad() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/admin/users")
                .param("ids", "1")
                .param("from", "ad")
                .param("size", "10");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("Failed to convert value of type 'java.lang.String' to " +
                        "required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"ad\""),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void getUsersTestNotValidSizeBad() throws Exception {
        var request = MockMvcRequestBuilders
                .get("/admin/users")
                .param("ids", "1")
                .param("from", "0")
                .param("size", "ad");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("Failed to convert value of type 'java.lang.String' to " +
                        "required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"ad\""),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void addUserTestValid() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"ivan.petrov@practicummail.ru\",\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        Mockito.when(adminUserService.addUser(Mockito.any(NewUserRequest.class)))
                .thenReturn(UserDtoResponse.builder()
                        .id(1L)
                        .name("Иван Петров")
                        .email("ivan.petrov@practicummail.ru")
                        .build());

        this.mockMvc.perform(request).andExpectAll(
                status().isCreated(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("{\"email\":\"ivan.petrov@practicummail.ru\",\"id\":1,\"name\":\"Иван Петров\"}")
        );

        Mockito.verify(adminUserService, Mockito.times(1))
                .addUser(Mockito.any(NewUserRequest.class));
    }

    @Test
    void addUserTestNotValidEmailBlank() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists()
        );
    }

    @Test
    void addUserTestNotValidEmailFail() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"ivan.petrovpracticummail.ru\",\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists());
    }

    @Test
    void addUserTestNotValidEmail5() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"t@m.r\",\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists());
    }

    @Test
    void addUserTestNotValidName251() throws Exception {
        String nameWith255Chars = "a".repeat(251);

        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"ivan.petrovpracticummail.ru\",\n" +
                        "  \"name\": \"" + nameWith255Chars + "\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists());
    }


    @Test
    void addUserTestNotValidEmail255() throws Exception {
        StringBuilder emailWith255Chars = new StringBuilder("ivan");
        String s = "a".repeat(242);
        emailWith255Chars.append(s);
        emailWith255Chars.append("@test.com");

        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"" + emailWith255Chars + "\",\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists());
    }

    @Test
    void addUserTestNotValidEmailDuplicate() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"ivan.petrov@practicummail.ru\",\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        Mockito.when(adminUserService.addUser(Mockito.any(NewUserRequest.class)))
                .thenThrow(new DataIntegrityViolationException("could not execute statement; SQL [n/a]; " +
                        "constraint [uq_email]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"));

        this.mockMvc.perform(request).andExpectAll(
                status().isConflict(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Conflict"),
                jsonPath("status").value("CONFLICT"),
                jsonPath("message").value("could not execute statement; SQL [n/a]; " +
                        "constraint [uq_email]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"),
                jsonPath("timestamp").exists());

        Mockito.verify(adminUserService, Mockito.times(1))
                .addUser(Mockito.any(NewUserRequest.class));
    }

    @Test
    void addUserTestNotValidNameBlank() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"ivan.petrov@practicummail.ru\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists());
    }

    @Test
    void addUserTestNotValidName1() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\": \"ivan.petrov@practicummail.ru\",\n" +
                        "  \"name\": \"a\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").exists(),
                jsonPath("timestamp").exists());
    }


    @Test
    void deleteUserValid() throws Exception {
        var request = MockMvcRequestBuilders
                .delete("/admin/users/1");

        Mockito.doNothing().when(adminUserService).deleteUser(Mockito.anyLong());

        this.mockMvc.perform(request).andExpectAll(
                status().isNoContent()
        );

        Mockito.verify(adminUserService, Mockito.times(1))
                .deleteUser(Mockito.anyLong());
    }

    @Test
    void deleteUserNoValid() throws Exception {
        var request = MockMvcRequestBuilders
                .delete("/admin/users/1");

        Mockito.doThrow(new NotFoundUserException("Пользователь с id 1 не найден")).when(adminUserService).deleteUser(Mockito.anyLong());

        this.mockMvc.perform(request).andExpectAll(
                status().isNotFound(),
                jsonPath("reason").value("Not Found"),
                jsonPath("status").value("NOT_FOUND"),
                jsonPath("message").value("Пользователь с id 1 не найден"),
                jsonPath("timestamp").exists()
        );

        Mockito.verify(adminUserService, Mockito.times(1))
                .deleteUser(Mockito.anyLong());
    }
}