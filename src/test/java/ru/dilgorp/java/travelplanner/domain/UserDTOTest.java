package ru.dilgorp.java.travelplanner.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserDTOTest {

    @Test
    void equalsIsCorrect(){
        UserDTO userDTO1 = new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        assertEquals(userDTO1, userDTO2);

        // username
        userDTO1.setUsername("setUsername");
        checkBothNotEquals(userDTO1, userDTO2);

        userDTO2.setUsername("userDTO1.setUsername");
        checkBothNotEquals(userDTO1, userDTO2);

        userDTO2.setUsername(userDTO1.getUsername());
        assertEquals(userDTO1, userDTO2);

        // password
        userDTO1.setPassword("setPassword");
        checkBothNotEquals(userDTO1, userDTO2);

        userDTO2.setPassword("userDTO1.setPassword");
        checkBothNotEquals(userDTO1, userDTO2);

        userDTO2.setPassword(userDTO1.getPassword());

        //then
        finalCheckForEquals(userDTO1, userDTO2);
    }

    @Test
    void hashCodeIsCorrect(){
        UserDTO userDTO1 = new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        assertEquals(userDTO1.hashCode(), userDTO2.hashCode());

        // username
        userDTO1.setUsername("setUsername");
        userDTO2.setUsername(userDTO1.getUsername());

        // password
        userDTO1.setPassword("setPassword");
        userDTO2.setPassword(userDTO1.getPassword());

        //then
        assertEquals(userDTO1.hashCode(), userDTO2.hashCode());
    }

    @Test
    void toStringIsCorrect(){
        String string = "UserDTO(username=setUsername, password=setPassword)";

        UserDTO userDTO = new UserDTO();

        // username
        userDTO.setUsername("setUsername");

        // password
        userDTO.setPassword("setPassword");

        System.out.println(userDTO);
        //then
        assertEquals(string, userDTO.toString());
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(UserDTO userDTO1, UserDTO userDTO2) {
        assertNotEquals(null, userDTO1);
        assertNotEquals("", userDTO1);
        assertEquals(userDTO1, userDTO2);
        assertEquals(userDTO2, userDTO1);
    }

    private void checkBothNotEquals(UserDTO userDTO1, UserDTO userDTO2) {
        assertNotEquals(userDTO1, userDTO2);
        assertNotEquals(userDTO2, userDTO1);
    }
}