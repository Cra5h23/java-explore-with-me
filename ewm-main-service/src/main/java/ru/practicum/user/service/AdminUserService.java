package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public interface AdminUserService {
    List<UserDtoResponse> getUsers(List<Long> ids, int from, int size);

    UserDtoResponse addUser(NewUserRequest user);

    void deleteUser(Long userId);
}
