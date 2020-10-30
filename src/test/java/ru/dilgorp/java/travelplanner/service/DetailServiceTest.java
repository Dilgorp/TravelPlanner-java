package ru.dilgorp.java.travelplanner.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.domain.Role;
import ru.dilgorp.java.travelplanner.domain.User;
import ru.dilgorp.java.travelplanner.repository.UserRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig({RepositoriesConfig.class})
class DetailServiceTest {
    @Autowired
    private UserRepository userRepository;

    private DetailService detailService;

    @BeforeEach
    void setUp() {
        detailService = new DetailService(
                userRepository
        );
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect() {
        assertNotNull(detailService);
    }

    @Test
    void loadUserByUsernameIsCorrect() {
        // given
        String username = "TestUser";
        User user = new User(username, "123");
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );

        // when
        UserDetails userDetailsByService = detailService.loadUserByUsername(username);

        // then
        assertEquals(userDetails, userDetailsByService);
    }

    @Test
    void loadUserByUsernameNotFound() {
        // given
        String username = "TestUser";

        // when
        Exception exception = null;
        try {
            detailService.loadUserByUsername(username);
        } catch (Exception e) {
            exception = e;
        }

        // then
        assertNotNull(exception);
        assertEquals(UsernameNotFoundException.class, exception.getClass());
        assertEquals("User with username " + username + " was not found", exception.getMessage());
    }

}