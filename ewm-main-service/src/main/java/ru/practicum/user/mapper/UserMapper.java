package ru.practicum.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.model.User;

/**
 * Маппер для преобразования сущности {@link User} в dto.
 *
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Component
public class UserMapper {
    /**
     * Метод преобразования Dto {@link NewUserRequest} в сущность {@link User}.
     *
     * @param user данные пользователя.
     * @return {@link User} сущность пользователя.
     */
    public User toUser(NewUserRequest user) {
        return User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    /**
     * Метод преобразования сущности {@link User} в Dto {@link UserDtoResponse}.
     *
     * @param user данные пользователя.
     * @return {@link UserDtoResponse} Dto пользователя.
     */
    public UserDtoResponse toUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
