package ru.mtsteta.courses.service;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.mtsteta.courses.dao.UserRepository;
import ru.mtsteta.courses.domain.Role;
import ru.mtsteta.courses.domain.User;
import ru.mtsteta.courses.dto.UserDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void createUser() {
        String username = "user_new";
        UserDto userNew = userService.createUser(username);
        assertEquals(username, userNew.getUsername());

        Set<Role> roles = userNew.getRoles();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertTrue(roles.stream()
                .allMatch(role -> role.getName().equals("ROLE_STUDENT")));
    }

    @Test
    @Transactional
    void createUserException() {
        assertThrows(Exception.class, () -> userService.createUser("user"));
    }

    @Test
    public void testLazyLesson() {
        User user = userRepository.findById(1L).get();

        assertThrows(LazyInitializationException.class, () -> user.getAvatarImage().getFilename());

    }
}