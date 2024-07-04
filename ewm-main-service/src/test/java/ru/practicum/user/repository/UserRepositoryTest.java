package ru.practicum.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.user.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author Nikolay Radzivon
 * @Date 05.07.2024
 */
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        List<User> list = makeUser(3);
        list.forEach(userRepository::save);
    }

    private List<User> makeUser(int count) {
        return IntStream.rangeClosed(1, count).mapToObj(i -> User.builder()
                .name("testUser" + i)
                .email("testEmail" + i + "@test.com")
                .build()).collect(Collectors.toList());
    }

    @Test
    public void testFindByIdIn() {
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);
        Pageable pageable = PageRequest.of(0, 10);

        Page<User> usersPage = userRepository.findByIdIn(userIds, pageable);

        assertThat(usersPage).isNotNull();
        assertThat(usersPage.getContent()).hasSize(3);
        assertThat(usersPage.getContent()).extracting(User::getId).containsAll(userIds);
        assertThat(usersPage.getContent().get(0).getName()).isEqualTo("testUser1");
        assertThat(usersPage.getContent().get(1).getName()).isEqualTo("testUser2");
        assertThat(usersPage.getContent().get(2).getName()).isEqualTo("testUser3");
        assertThat(usersPage.getContent().get(0).getId()).isEqualTo(1);
        assertThat(usersPage.getContent().get(1).getId()).isEqualTo(2);
        assertThat(usersPage.getContent().get(2).getId()).isEqualTo(3);
        assertThat(usersPage.getContent().get(0).getEmail()).isEqualTo("testEmail1@test.com");
        assertThat(usersPage.getContent().get(1).getEmail()).isEqualTo("testEmail2@test.com");
        assertThat(usersPage.getContent().get(2).getEmail()).isEqualTo("testEmail3@test.com");
    }
}