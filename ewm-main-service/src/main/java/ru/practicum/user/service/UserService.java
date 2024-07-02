package ru.practicum.user.service;

import ru.practicum.user.model.User;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
public interface UserService {
    User checkUser(Long userId);
}
