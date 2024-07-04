package ru.practicum.user.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.exception.NotFoundUserException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Nikolay Radzivon
 * @Date 05.07.2024
 */
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void checkUserTestValid() {
        Long userId = 1L;
        User expectedUser = new User(userId, "test", "testEmail@test.com");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.checkUser(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void checkUserTestNotValid() {
        Long userId = 2L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, () -> userService.checkUser(userId));
    }
}
