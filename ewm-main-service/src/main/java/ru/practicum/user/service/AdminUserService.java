package ru.practicum.user.service;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDtoResponse;

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
