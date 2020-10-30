package ru.dilgorp.java.travelplanner.domain.google.api;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserRequestTest {

    @Test
    void equalsIsCorrect() {
        UserRequest userRequest1 = new UserRequest();
        UserRequest userRequest2 = new UserRequest();
        assertEquals(userRequest1, userRequest2);

        //uuid
        userRequest1.setUuid(UUID.randomUUID());
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setUuid(UUID.randomUUID());
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setUuid(userRequest1.getUuid());
        assertEquals(userRequest1, userRequest2);

        // text
        userRequest1.setText("setText");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setText("userRequest1.setText");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setText(userRequest1.getText());
        assertEquals(userRequest1, userRequest2);

        // formattedAddress
        userRequest1.setFormattedAddress("setFormattedAddress");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setFormattedAddress("userRequest2.setFormattedAddress");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setFormattedAddress(userRequest1.getFormattedAddress());
        assertEquals(userRequest1, userRequest2);

        // name
        userRequest1.setName("setName");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setName("userRequest2.setName");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setName(userRequest1.getName());
        assertEquals(userRequest1, userRequest2);

        // imagePath
        userRequest1.setImagePath("setImagePath");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setImagePath("userRequest2.setImagePath");
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setImagePath(userRequest1.getImagePath());
        assertEquals(userRequest1, userRequest2);

        // imagePath
        userRequest1.setExpired(new Date());
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setExpired(new Date(0));
        checkBothNotEquals(userRequest1, userRequest2);

        userRequest2.setExpired(userRequest1.getExpired());

        //then
        finalCheckForEquals(userRequest1, userRequest2);
    }

    @Test
    void hashCodeIsCorrect(){
        UserRequest userRequest1 = new UserRequest();
        UserRequest userRequest2 = new UserRequest();
        assertEquals(userRequest1.hashCode(), userRequest2.hashCode());

        //uuid
        userRequest1.setUuid(UUID.randomUUID());
        userRequest2.setUuid(userRequest1.getUuid());

        // text
        userRequest1.setText("setText");
        userRequest2.setText(userRequest1.getText());

        // formattedAddress
        userRequest1.setFormattedAddress("setFormattedAddress");
        userRequest2.setFormattedAddress(userRequest1.getFormattedAddress());

        // name
        userRequest1.setName("setName");
        userRequest2.setName(userRequest1.getName());

        // imagePath
        userRequest1.setImagePath("setImagePath");
        userRequest2.setImagePath(userRequest1.getImagePath());

        // imagePath
        userRequest1.setExpired(new Date());
        userRequest2.setExpired(userRequest1.getExpired());

        //then
        assertEquals(userRequest1.hashCode(), userRequest2.hashCode());
    }

    @Test
    void toStringIsCorrect(){
        String string =
                "UserRequest(" +
                        "uuid=420d0a0e-7d6c-40f9-88b0-2ef607ad996b, " +
                        "text=setText, " +
                        "formattedAddress=setFormattedAddress, " +
                        "name=setName, " +
                        "imagePath=setImagePath, " +
                        "expired=Thu Jan 01 05:00:00 YEKT 1970" +
                        ")";
        UserRequest userRequest = new UserRequest();
        userRequest.setUuid(UUID.fromString("420d0a0e-7d6c-40f9-88b0-2ef607ad996b"));
        userRequest.setText("setText");
        userRequest.setFormattedAddress("setFormattedAddress");
        userRequest.setName("setName");
        userRequest.setImagePath("setImagePath");
        userRequest.setExpired(new Date(0));
        assertEquals(string, userRequest.toString());
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(UserRequest userRequest1, UserRequest userRequest2) {
        assertNotEquals(null, userRequest1);
        assertNotEquals("", userRequest1);
        assertEquals(userRequest1, userRequest2);
        assertEquals(userRequest2, userRequest1);
    }

    private void checkBothNotEquals(UserRequest userRequest1, UserRequest userRequest2) {
        assertNotEquals(userRequest1, userRequest2);
        assertNotEquals(userRequest2, userRequest1);
    }
}