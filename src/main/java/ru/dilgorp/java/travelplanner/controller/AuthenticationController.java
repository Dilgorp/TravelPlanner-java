package ru.dilgorp.java.travelplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.Role;
import ru.dilgorp.java.travelplanner.domain.User;
import ru.dilgorp.java.travelplanner.domain.UserDTO;
import ru.dilgorp.java.travelplanner.repository.UserRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/registration")
    public Response<User> postRegistration(@RequestBody UserDTO user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return new Response<>(ResponseType.ERROR, "Пользователь уже существует", null);
        }

        User userForSaving = new User(user.getUsername(), encoder.encode(user.getPassword()));
        userForSaving.setRoles(Collections.singleton(Role.USER));
        userRepository.save(userForSaving);

        return new Response<>(ResponseType.SUCCESS, "", userForSaving);
    }

    @PostMapping("/login")
    public Response<User> getLogin(@RequestBody UserDTO user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        return new Response<>(ResponseType.SUCCESS, "", userDB);
    }
}
