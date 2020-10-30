package ru.dilgorp.java.travelplanner.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CityTest {

    @Test
    void equalsIsCorrect(){
        City city1 = new City();
        City city2 = new City();

        assertEquals(city1, city2);

        //uuid
        city1.setUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setUuid(city1.getUuid());
        assertEquals(city1, city2);

        // name;
        city1.setName("setName");
        checkBothNotEquals(city1, city2);

        city2.setName("city2.setName");
        checkBothNotEquals(city1, city2);

        city2.setName(city1.getName());
        assertEquals(city1, city2);

        // placesCount;
        city1.setPlacesCount(2);
        checkBothNotEquals(city1, city2);

        city2.setPlacesCount(3);
        checkBothNotEquals(city1, city2);

        city2.setPlacesCount(city1.getPlacesCount());
        assertEquals(city1, city2);

        // description;
        city1.setDescription("setDescription");
        checkBothNotEquals(city1, city2);

        city2.setDescription("city2.setDescription");
        checkBothNotEquals(city1, city2);

        city2.setDescription(city1.getDescription());
        assertEquals(city1, city2);

        // imagePath;
        city1.setImagePath("setImagePath");
        checkBothNotEquals(city1, city2);

        city2.setImagePath("city2.setImagePath");
        checkBothNotEquals(city1, city2);

        city2.setImagePath(city1.getImagePath());
        assertEquals(city1, city2);

        checkUuids(city1, city2);

        // travelNumber;
        city1.setTravelNumber(2);
        checkBothNotEquals(city1, city2);

        city2.setTravelNumber(3);
        checkBothNotEquals(city1, city2);

        city2.setTravelNumber(city1.getTravelNumber());

        //then
        finalCheckForEquals(city1, city2);
    }

    private void checkUuids(City city1, City city2) {
        // travelUuid;
        city1.setTravelUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setTravelUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setTravelUuid(city1.getTravelUuid());
        assertEquals(city1, city2);

        // userUuid;
        city1.setUserUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setUserUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setUserUuid(city1.getUserUuid());
        assertEquals(city1, city2);

        // userRequestUuid;
        city1.setUserRequestUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setUserRequestUuid(UUID.randomUUID());
        checkBothNotEquals(city1, city2);

        city2.setUserRequestUuid(city1.getUserRequestUuid());
        assertEquals(city1, city2);
    }

    @Test
    void hashCodeIsCorrect(){
        City city1 = new City();
        City city2 = new City();

        assertEquals(city1.hashCode(), city2.hashCode());

        //uuid
        city1.setUuid(UUID.randomUUID());
        city2.setUuid(city1.getUuid());

        // name;
        city1.setName("setName");
        city2.setName(city1.getName());

        // placesCount;
        city1.setPlacesCount(2);
        city2.setPlacesCount(city1.getPlacesCount());

        // description;
        city1.setDescription("setDescription");
        city2.setDescription(city1.getDescription());

        // imagePath;
        city1.setImagePath("setImagePath");
        city2.setImagePath(city1.getImagePath());

        // travelUuid;
        city1.setTravelUuid(UUID.randomUUID());
        city2.setTravelUuid(city1.getTravelUuid());

        // userUuid;
        city1.setUserUuid(UUID.randomUUID());
        city2.setUserUuid(city1.getUserUuid());

        // userRequestUuid;
        city1.setUserRequestUuid(UUID.randomUUID());
        city2.setUserRequestUuid(city1.getUserRequestUuid());

        // travelNumber;
        city1.setTravelNumber(2);
        city2.setTravelNumber(city1.getTravelNumber());

        //then
        assertEquals(city1.hashCode(), city2.hashCode());
    }

    @Test
    void toStringIsCorrect(){
        String string =
                "City(" +
                        "uuid=cca93e08-4f44-4dc6-a0da-e7686dad8237, " +
                        "name=setName, " +
                        "placesCount=2, " +
                        "description=setDescription, " +
                        "imagePath=setImagePath, " +
                        "travelUuid=ec3d9486-aeaf-4071-b0dc-6ee925cb4c0a, " +
                        "userUuid=17ed68bc-2800-4cf1-85c2-631430b12d5d, " +
                        "userRequestUuid=7b60c380-137c-4bd4-a2c3-e953630d494c, " +
                        "travelNumber=2" +
                        ")";
        City city = new City();

        //uuid
        city.setUuid(UUID.fromString("cca93e08-4f44-4dc6-a0da-e7686dad8237"));

        // name;
        city.setName("setName");

        // placesCount;
        city.setPlacesCount(2);

        // description;
        city.setDescription("setDescription");

        // imagePath;
        city.setImagePath("setImagePath");

        // travelUuid;
        city.setTravelUuid(UUID.fromString("ec3d9486-aeaf-4071-b0dc-6ee925cb4c0a"));

        // userUuid;
        city.setUserUuid(UUID.fromString("17ed68bc-2800-4cf1-85c2-631430b12d5d"));

        // userRequestUuid;
        city.setUserRequestUuid(UUID.fromString("7b60c380-137c-4bd4-a2c3-e953630d494c"));

        // travelNumber;
        city.setTravelNumber(2);
        assertEquals(string, city.toString());
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(City city1, City city2) {
        assertNotEquals(null, city1);
        assertNotEquals("", city1);
        assertEquals(city1, city2);
        assertEquals(city2, city1);
    }

    private void checkBothNotEquals(City city1, City city2) {
        assertNotEquals(city1, city2);
        assertNotEquals(city2, city1);
    }

}