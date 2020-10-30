package ru.dilgorp.java.travelplanner.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.ControllersConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.domain.Role;
import ru.dilgorp.java.travelplanner.domain.User;
import ru.dilgorp.java.travelplanner.domain.UserDTO;
import ru.dilgorp.java.travelplanner.repository.UserRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig({RepositoriesConfig.class, ControllersConfig.class})
class AuthenticationControllerTest {

    private AuthenticationController controller;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        controller = new AuthenticationController(
                userRepository,
                encoder
        );
    }

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect() {
        assertNotNull(controller);
    }

    @Test
    void postRegistrationIsCorrect() {
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("TestUser");

        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        user.setRoles(Collections.singleton(Role.USER));
        Response<User> response = new Response<>(ResponseType.SUCCESS, "", user);

        // when
        Response<User> controllerResponse = controller.postRegistration(userDTO);

        // then
        assertEquals(response.getType(), controllerResponse.getType());
        assertEquals(response.getMessage(), controllerResponse.getMessage());
        assertEquals(response.getData().getUsername(), controllerResponse.getData().getUsername());
        assertTrue(
                encoder.matches(response.getData().getPassword(), controllerResponse.getData().getPassword())
        );
    }

    @Test
    void postRegistrationUserExist() {
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("TestUser");

        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        Response<User> response = new Response<>(ResponseType.ERROR, "Пользователь уже существует", null);

        // when
        Response<User> controllerResponse = controller.postRegistration(userDTO);

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void loginIsCorrect(){
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("TestUser");

        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        Response<User> response = new Response<>(ResponseType.SUCCESS, "", user);

        // when
        Response<User> controllerResponse = controller.getLogin(userDTO);

        // then
        assertEquals(response, controllerResponse);
    }
}