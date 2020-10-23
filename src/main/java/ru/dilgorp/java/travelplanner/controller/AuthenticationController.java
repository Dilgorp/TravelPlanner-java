package ru.dilgorp.java.travelplanner.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.Role;
import ru.dilgorp.java.travelplanner.domain.User;
import ru.dilgorp.java.travelplanner.repository.UserRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.Collections;

@SuppressWarnings("unused")
@RestController
public class AuthenticationController {

    public static final String REGISTRATION_PATH = "/registration";
    public static final String LOGIN_PATH = "/login";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public AuthenticationController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @RequestMapping(value = REGISTRATION_PATH, method = RequestMethod.POST)
    public Response<User> postRegistration(@RequestBody User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return new Response<>(ResponseType.ERROR, "Пользователь уже существует", null);
        }

        User userForSaving = new User(user.getUsername(), encoder.encode(user.getPassword()));
        userForSaving.setRoles(Collections.singleton(Role.USER));
        userRepository.save(userForSaving);

        return new Response<>(ResponseType.SUCCESS, "", userForSaving);
    }

    @RequestMapping(value = LOGIN_PATH, method = RequestMethod.POST)
    public Response<User> getLogin(@RequestBody User user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        return new Response<>(ResponseType.SUCCESS, "", userDB);
    }
}
