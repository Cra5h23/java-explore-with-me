package ru.practicum.user.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDtoResponse;
import ru.practicum.exception.NotFoundUserException;
import ru.practicum.user.service.AdminUserService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@WebMvcTest(controllers = AdminUserController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
                        // "  \"email\": \"ivan.petrov@practicummail.ru\",\n" +
                        "  \"name\": \"Иван Петров\"\n" +
                        "}");

        this.mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("reason").value("Bad Request"),
                jsonPath("status").value("BAD_REQUEST"),
                jsonPath("message").value("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Object> ru.practicum.user.controller.AdminUserController.addUser(ru.practicum.dto.user.NewUserRequest): [Field error in object 'newUserRequest' on field 'email': rejected value [null]; codes [NotBlank.newUserRequest.email,NotBlank.email,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [newUserRequest.email,email]; arguments []; default message [email]]; default message [Адрес электронной почты не может быть пустым]] "),
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
                //todo сделать
                //  jsonPath("message").value("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Object> ru.practicum.user.controller.AdminUserController.addUser(ru.practicum.dto.user.NewUserRequest): [Field error in object 'newUserRequest' on field 'email': rejected value [ivan.petrovpracticummail.ru]; codes [Email.newUserRequest.email,Email.email,Email.java.lang.String,Email]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [newUserRequest.email,email]; arguments []; default message [email],[Ljavax.validation.constraints.Pattern$Flag;@69ffdaa8,.*]; default message [must be a well-formed email address]] "),
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
                jsonPath("message").value("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Object> ru.practicum.user.controller.AdminUserController.addUser(ru.practicum.dto.user.NewUserRequest): [Field error in object 'newUserRequest' on field 'email': rejected value [t@m.r]; codes [Size.newUserRequest.email,Size.email,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [newUserRequest.email,email]; arguments []; default message [email],254,6]; default message [Адрес электронной почты не может быть меньше 6 и больше 254 символов]] "),
                jsonPath("timestamp").exists());
    }

    //todo добавить тест когда email больше 254 символов
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
                jsonPath("message").value("Validation failed for argument [0] in public " +
                        "org.springframework.http.ResponseEntity<java.lang.Object> ru.practicum.user.controller.AdminUserController.addUser(ru.practicum.dto.user.NewUserRequest): [Field error in object 'newUserRequest' on field 'name': rejected value [null]; codes [NotBlank.newUserRequest.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [newUserRequest.name,name]; arguments []; default message [name]]; default message [Имя пользователя не может быть пустым]] "),
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
                jsonPath("message").value("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Object> ru.practicum.user.controller.AdminUserController.addUser(ru.practicum.dto.user.NewUserRequest): [Field error in object 'newUserRequest' on field 'name': rejected value [a]; codes [Size.newUserRequest.name,Size.name,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [newUserRequest.name,name]; arguments []; default message [name],250,2]; default message [Имя пользователя не может быть меньше 2 и больше 250 символов]] "),
                jsonPath("timestamp").exists());
    }
//todo добавить тест когда имя больше 250 символов

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