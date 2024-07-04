package ru.practicum.user.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Nikolay Radzivon
 * @Date 05.07.2024
 */
@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void testToUser() {
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setEmail("test@example.com");
        newUserRequest.setName("Test Name");

        User user = userMapper.toUser(newUserRequest);

        assertEquals(newUserRequest.getEmail(), user.getEmail());
        assertEquals(newUserRequest.getName(), user.getName());
    }

    @Test
    public void testToUserDtoResponse() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test Name");

        UserDtoResponse userDtoResponse = userMapper.toUserDtoResponse(user);

        assertEquals(user.getId(), userDtoResponse.getId());
        assertEquals(user.getEmail(), userDtoResponse.getEmail());
        assertEquals(user.getName(), userDtoResponse.getName());
    }
}