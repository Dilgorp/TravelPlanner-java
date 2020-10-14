package ru.dilgorp.java.travelplanner.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.Role;
import ru.dilgorp.java.travelplanner.domain.User;
import ru.dilgorp.java.travelplanner.repository.UserRepository;
import ru.dilgorp.java.travelplanner.response.AuthenticationResponse;
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

    @PostMapping(REGISTRATION_PATH)
    public AuthenticationResponse postRegistration(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if(userFromDb != null){
            return new AuthenticationResponse(ResponseType.ERROR, "Пользователь уже существует");
        }

        User userForSaving = new User(user.getUsername(), encoder.encode(user.getPassword()));
        userForSaving.setRoles(Collections.singleton(Role.USER));
        userRepository.save(userForSaving);

        return new AuthenticationResponse(ResponseType.SUCCESS, null);
    }

    @GetMapping(LOGIN_PATH)
    public AuthenticationResponse getLogin(){
        return new AuthenticationResponse(ResponseType.SUCCESS, null);
    }
}
