package ru.dilgorp.java.travelplanner.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {

    @Test
    void equalsIsCorrect() {
        User user1 = new User();
        User user2 = new User();
        assertEquals(user1, user2);

        // uuid;
        user1.setUuid(UUID.randomUUID());
        checkBothNotEquals(user1, user2);

        user2.setUuid(UUID.randomUUID());
        checkBothNotEquals(user1, user2);

        user2.setUuid(user1.getUuid());
        assertEquals(user1, user2);

        // username;
        user1.setUsername("setUsername");
        checkBothNotEquals(user1, user2);

        user2.setUsername("user2.setUsername");
        checkBothNotEquals(user1, user2);

        user2.setUsername(user1.getUsername());
        assertEquals(user1, user2);

        // password;
        user1.setPassword("setPassword");
        checkBothNotEquals(user1, user2);

        user2.setPassword("user2.setPassword");
        checkBothNotEquals(user1, user2);

        user2.setPassword(user1.getPassword());
        assertEquals(user1, user2);

        // roles;
        user1.setRoles(Collections.singleton(Role.USER));
        checkBothNotEquals(user1, user2);

        user2.setRoles(Collections.emptySet());
        checkBothNotEquals(user1, user2);

        user2.setRoles(Collections.singleton(null));
        checkBothNotEquals(user1, user2);

        user2.setRoles(user1.getRoles());

        //then
        finalCheckForEquals(user1, user2);
    }

    @Test
    void hashCodeIsCorrect() {
        User user1 = new User();
        User user2 = new User();
        assertEquals(user1.hashCode(), user2.hashCode());

        // uuid;
        user1.setUuid(UUID.randomUUID());
        user2.setUuid(user1.getUuid());

        // username;
        user1.setUsername("setUsername");
        user2.setUsername(user1.getUsername());

        // password;
        user1.setPassword("setPassword");
        user2.setPassword(user1.getPassword());

        // roles;
        user1.setRoles(Collections.singleton(Role.USER));
        user2.setRoles(user1.getRoles());

        //then
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void toStringIsCorrect() {
        String string =
                "User(" +
                        "uuid=7539a050-3b73-438a-a848-3482443d9cf9, " +
                        "username=setUsername, " +
                        "password=setPassword, " +
                        "roles=[USER]" +
                        ")";
        User user = new User();

        // uuid;
        user.setUuid(UUID.fromString("7539a050-3b73-438a-a848-3482443d9cf9"));

        // username;
        user.setUsername("setUsername");

        // password;
        user.setPassword("setPassword");

        // roles;
        user.setRoles(Collections.singleton(Role.USER));

        assertEquals(string, user.toString());
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(User user1, User user2) {
        assertNotEquals(null, user1);
        assertNotEquals("", user1);
        assertEquals(user1, user2);
        assertEquals(user2, user1);
    }

    private void checkBothNotEquals(User user1, User user2) {
        assertNotEquals(user1, user2);
        assertNotEquals(user2, user1);
    }
}