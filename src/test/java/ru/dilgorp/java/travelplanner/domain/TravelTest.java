package ru.dilgorp.java.travelplanner.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TravelTest {

    @Test
    void equalsIsCorrect() {
        Travel travel1 = new Travel();
        Travel travel2 = new Travel();

        assertEquals(travel1, travel2);

        //uuid
        travel1.setUuid(UUID.randomUUID());
        checkBothNotEquals(travel1, travel2);

        travel2.setUuid(UUID.randomUUID());
        checkBothNotEquals(travel1, travel2);

        travel2.setUuid(travel1.getUuid());
        assertEquals(travel1, travel2);

        // name
        travel1.setName("setName");
        checkBothNotEquals(travel1, travel2);

        travel2.setName("travel1.setName");
        checkBothNotEquals(travel1, travel2);

        travel2.setName(travel1.getName());
        assertEquals(travel1, travel2);

        // citiesCount;
        travel1.setCitiesCount(2);
        checkBothNotEquals(travel1, travel2);

        travel2.setCitiesCount(3);
        checkBothNotEquals(travel1, travel2);

        travel2.setCitiesCount(travel1.getCitiesCount());
        assertEquals(travel1, travel2);

        // placesCount;
        travel1.setPlacesCount(2);
        checkBothNotEquals(travel1, travel2);

        travel2.setPlacesCount(3);
        checkBothNotEquals(travel1, travel2);

        travel2.setPlacesCount(travel1.getPlacesCount());
        assertEquals(travel1, travel2);

        // imagePath;
        travel1.setImagePath("setImagePath");
        checkBothNotEquals(travel1, travel2);

        travel2.setImagePath("travel1.setImagePath");
        checkBothNotEquals(travel1, travel2);

        travel2.setImagePath(travel1.getImagePath());
        assertEquals(travel1, travel2);

        // userUuid;
        travel1.setUserUuid(UUID.randomUUID());
        checkBothNotEquals(travel1, travel2);

        travel2.setUserUuid(UUID.randomUUID());
        checkBothNotEquals(travel1, travel2);

        travel2.setUserUuid(travel1.getUserUuid());
        assertEquals(travel1, travel2);

        //then
        finalCheckForEquals(travel1, travel2);
    }

    @Test
    void hashCodeIsCorrect() {
        Travel travel1 = new Travel();
        Travel travel2 = new Travel();

        assertEquals(travel1.hashCode(), travel2.hashCode());

        //uuid
        travel1.setUuid(UUID.randomUUID());
        travel2.setUuid(travel1.getUuid());

        // name
        travel1.setName("setName");
        travel2.setName(travel1.getName());

        // citiesCount;
        travel1.setCitiesCount(2);
        travel2.setCitiesCount(travel1.getCitiesCount());

        // placesCount;
        travel1.setPlacesCount(2);
        travel2.setPlacesCount(travel1.getPlacesCount());

        // imagePath;
        travel1.setImagePath("setImagePath");
        travel2.setImagePath(travel1.getImagePath());

        // userUuid;
        travel1.setUserUuid(UUID.randomUUID());
        travel2.setUserUuid(travel1.getUserUuid());

        //then
        assertEquals(travel1.hashCode(), travel2.hashCode());
    }

    @Test
    void toStringIsCorrect() {
        String string =
                "Travel(" +
                        "uuid=11ff9a69-2378-4c43-8eb2-f1ae49d01dc0, " +
                        "name=setName, " +
                        "citiesCount=2, " +
                        "placesCount=2, " +
                        "imagePath=setImagePath, " +
                        "userUuid=6af705b4-b197-45e3-bd4d-0a6e81fb0e4e)";

        Travel travel = new Travel();

        //uuid
        travel.setUuid(UUID.fromString("11ff9a69-2378-4c43-8eb2-f1ae49d01dc0"));

        // name
        travel.setName("setName");

        // citiesCount;
        travel.setCitiesCount(2);

        // placesCount;
        travel.setPlacesCount(2);

        // imagePath;
        travel.setImagePath("setImagePath");

        // userUuid;
        travel.setUserUuid(UUID.fromString("6af705b4-b197-45e3-bd4d-0a6e81fb0e4e"));

        //then
        assertEquals(string, travel.toString());
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(Travel travel1, Travel travel2) {
        assertNotEquals(null, travel1);
        assertNotEquals("", travel1);
        assertEquals(travel1, travel2);
        assertEquals(travel2, travel1);
    }

    private void checkBothNotEquals(Travel travel1, Travel travel2) {
        assertNotEquals(travel1, travel2);
        assertNotEquals(travel2, travel1);
    }
}