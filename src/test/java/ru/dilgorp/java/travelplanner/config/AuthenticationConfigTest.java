package ru.dilgorp.java.travelplanner.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationConfigTest {

    @Test
    void bCryptPasswordEncoder() {
        assertNotNull(new AuthenticationConfig().bCryptPasswordEncoder());
    }
}