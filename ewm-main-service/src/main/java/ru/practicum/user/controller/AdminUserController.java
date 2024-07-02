package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.user.service.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<Object> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
            @Max(value = 100000, message = "Параметр size не может быть больше {value}") int size
    ) {
        log.info("GET /admin/users?ids={}&from={}&size={}", ids, from, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminUserService.getUsers(ids, from, size));
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody @Valid NewUserRequest user) {
        log.info("POST /admin/users body={}", user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminUserService.addUser(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        log.info("DELETE /admin/users/{}", userId);

        adminUserService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
