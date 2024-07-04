package ru.practicum.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.service.AdminUserService;
import ru.practicum.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * @param ids
     * @param from
     * @param size
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDtoResponse> getUsers(List<Long> ids, int from, int size) {
        var page = PageRequest.of(from < 0 ? from / size : 0, size);
        log.info("Запрошен список пользователей с параметрами ids={}, from={}, size={}", ids, from, size);

        if (ids == null) {
            return userRepository.findAll(page)
                    .stream()
                    .map(userMapper::toUserDtoResponse)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findByIdIn(ids, page)
                    .stream()
                    .map(userMapper::toUserDtoResponse)
                    .collect(Collectors.toList());
        }
    }

    /**
     * @param user
     * @return
     */
    @Override
    @Transactional
    public UserDtoResponse addUser(NewUserRequest user) {
        var u = userMapper.toUser(user);
        var save = userRepository.save(u);

        log.info("Добавлен новый пользователь {}", save);
        return userMapper.toUserDtoResponse(save);
    }

    /**
     * @param userId
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        var user = userService.checkUser(userId);

        log.info("Удалён пользователь {}", user);
        userRepository.delete(user);
    }
}
