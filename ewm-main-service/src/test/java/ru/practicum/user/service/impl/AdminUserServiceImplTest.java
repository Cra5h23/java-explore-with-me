package ru.practicum.user.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.exception.NotFoundUserException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nikolay Radzivon
 * @Date 05.07.2024
 */
@SpringBootTest
class AdminUserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    UserService userService;

    @InjectMocks
    AdminUserServiceImpl adminUserService;

    private List<User> makeUser(int count, boolean isId) {
        return IntStream.rangeClosed(1, count).mapToObj(i -> User.builder()
                .id(isId ? (long) i : null)
                .name("testName" + i)
                .email(String.format("testEmail%d@test.com", i))
                .build()).collect(Collectors.toList());
    }

    @Test
    void getUsersTestValid() {
        Mockito.when(userRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<User>(makeUser(3, true)));

        Mockito.when(userMapper.toUserDtoResponse(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    User argument = invocation.getArgument(0);
                    UserDtoResponse dto = new UserDtoResponse();
                    dto.setId(argument.getId());
                    dto.setName(argument.getName());
                    dto.setEmail(argument.getEmail());
                    return dto;
                });

        List<UserDtoResponse> users = adminUserService.getUsers(null, 0, 10);

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("testName1", users.get(0).getName());
        assertEquals("testName2", users.get(1).getName());
        assertEquals("testName3", users.get(2).getName());
        assertEquals(1, users.get(0).getId());
        assertEquals(2, users.get(1).getId());
        assertEquals(3, users.get(2).getId());
        assertEquals("testEmail1@test.com", users.get(0).getEmail());
        assertEquals("testEmail2@test.com", users.get(1).getEmail());
        assertEquals("testEmail3@test.com", users.get(2).getEmail());

        Mockito.verify(userRepository, Mockito.times(1))
                .findAll(Mockito.any(PageRequest.class));
        Mockito.verify(userMapper, Mockito.times(3))
                .toUserDtoResponse(Mockito.any(User.class));
    }

    @Test
    void getUsersTestValidIds123() {
        Mockito.when(userRepository.findByIdIn(Mockito.anyList(), Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<User>(makeUser(3, true)));

        Mockito.when(userMapper.toUserDtoResponse(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    User argument = invocation.getArgument(0);
                    UserDtoResponse dto = new UserDtoResponse();
                    dto.setId(argument.getId());
                    dto.setName(argument.getName());
                    dto.setEmail(argument.getEmail());
                    return dto;
                });

        List<UserDtoResponse> users = adminUserService.getUsers(List.of(1L, 2L, 3L), 0, 10);

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("testName1", users.get(0).getName());
        assertEquals("testName2", users.get(1).getName());
        assertEquals("testName3", users.get(2).getName());
        assertEquals(1, users.get(0).getId());
        assertEquals(2, users.get(1).getId());
        assertEquals(3, users.get(2).getId());
        assertEquals("testEmail1@test.com", users.get(0).getEmail());
        assertEquals("testEmail2@test.com", users.get(1).getEmail());
        assertEquals("testEmail3@test.com", users.get(2).getEmail());

        Mockito.verify(userRepository, Mockito.times(1))
                .findByIdIn(Mockito.anyList(), Mockito.any(PageRequest.class));
        Mockito.verify(userMapper, Mockito.times(3))
                .toUserDtoResponse(Mockito.any(User.class));
    }

    @Test
    void addUserTestValid() {
        Mockito.when(userMapper.toUser(Mockito.any(NewUserRequest.class)))
                .thenReturn(makeUser(1, false).get(0));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(makeUser(1, true).get(0));

        Mockito.when(userMapper.toUserDtoResponse(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    User argument = invocation.getArgument(0);
                    UserDtoResponse dto = new UserDtoResponse();
                    dto.setId(argument.getId());
                    dto.setName(argument.getName());
                    dto.setEmail(argument.getEmail());
                    return dto;
                });

        UserDtoResponse test = adminUserService.addUser(NewUserRequest.builder()
                .email("testEmail1@test.com")
                .name("testName1")
                .build());

        assertNotNull(test);
        assertEquals(1, test.getId());
        assertEquals("testName1", test.getName());
        assertEquals("testEmail1@test.com", test.getEmail());

        Mockito.verify(userMapper, Mockito.times(1))
                .toUser(Mockito.any(NewUserRequest.class));
        Mockito.verify(userRepository, Mockito.times(1))
                .save(Mockito.any(User.class));
        Mockito.verify(userMapper, Mockito.times(1))
                .toUserDtoResponse(Mockito.any(User.class));
    }

    @Test
    void deleteUserTestValid() {
        Mockito.when(userService.checkUser(Mockito.anyLong()))
                .thenReturn(makeUser(1, true).get(0));
        Mockito.doNothing().when(userRepository).delete(Mockito.any(User.class));

        adminUserService.deleteUser(1L);

        Mockito.verify(userService, Mockito.times(1))
                .checkUser(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(Mockito.any(User.class));
    }

    @Test
    void deleteUserTestNotValid() {
        Mockito.when(userService.checkUser(Mockito.anyLong()))
                        .thenThrow( new NotFoundUserException("Пользователь с id 1 не существует или не доступен"));

     assertThrows(NotFoundUserException.class, ()->  adminUserService.deleteUser(1L));

        Mockito.verify(userService, Mockito.times(1))
                .checkUser(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.never())
                .delete(Mockito.any(User.class));
    }
}