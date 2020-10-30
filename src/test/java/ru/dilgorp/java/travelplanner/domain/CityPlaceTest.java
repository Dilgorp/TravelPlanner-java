package ru.dilgorp.java.travelplanner.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CityPlaceTest {

    @Test
    void equalsIsCorrect(){
        CityPlace cityPlace1 = new CityPlace();
        CityPlace cityPlace2 = new CityPlace();
        assertEquals(cityPlace1, cityPlace2);

        // uuid;
        cityPlace1.setUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setUuid(cityPlace1.getUuid());
        assertEquals(cityPlace1, cityPlace2);

        // name;
        cityPlace1.setName("setName");
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setName("cityPlace2.setName");
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setName(cityPlace1.getName());
        assertEquals(cityPlace1, cityPlace2);

        // description;
        cityPlace1.setDescription("setDescription");
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setDescription("cityPlace2.setDescription");
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setDescription(cityPlace1.getDescription());
        assertEquals(cityPlace1, cityPlace2);

        // imagePath;
        cityPlace1.setImagePath("setImagePath");
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setImagePath("cityPlace2.setImagePath");
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setImagePath(cityPlace1.getImagePath());
        assertEquals(cityPlace1, cityPlace2);

        // cityUuid;
        cityPlace1.setCityUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setCityUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setCityUuid(cityPlace1.getCityUuid());
        assertEquals(cityPlace1, cityPlace2);

        // travelUuid;
        cityPlace1.setTravelUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setTravelUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setTravelUuid(cityPlace1.getTravelUuid());
        assertEquals(cityPlace1, cityPlace2);

        // userUuid;
        cityPlace1.setUserUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setUserUuid(UUID.randomUUID());
        checkBothNotEquals(cityPlace1, cityPlace2);

        cityPlace2.setUserUuid(cityPlace1.getUserUuid());

        //then
        finalCheckForEquals(cityPlace1, cityPlace2);
    }

    @Test
    void hashCodeIsCorrect(){
        CityPlace cityPlace1 = new CityPlace();
        CityPlace cityPlace2 = new CityPlace();
        assertEquals(cityPlace1.hashCode(), cityPlace2.hashCode());

        // uuid;
        cityPlace1.setUuid(UUID.randomUUID());
        cityPlace2.setUuid(cityPlace1.getUuid());

        // name;
        cityPlace1.setName("setName");
        cityPlace2.setName(cityPlace1.getName());

        // description;
        cityPlace1.setDescription("setDescription");
        cityPlace2.setDescription(cityPlace1.getDescription());

        // imagePath;
        cityPlace1.setImagePath("setImagePath");
        cityPlace2.setImagePath(cityPlace1.getImagePath());

        // cityUuid;
        cityPlace1.setCityUuid(UUID.randomUUID());
        cityPlace2.setCityUuid(cityPlace1.getCityUuid());

        // travelUuid;
        cityPlace1.setTravelUuid(UUID.randomUUID());
        cityPlace2.setTravelUuid(cityPlace1.getTravelUuid());

        // userUuid;
        cityPlace1.setUserUuid(UUID.randomUUID());
        cityPlace2.setUserUuid(cityPlace1.getUserUuid());

        //then
        assertEquals(cityPlace1.hashCode(), cityPlace2.hashCode());
    }

    @Test
    void toStringIsCorrect(){
        String string =
                "CityPlace(" +
                        "uuid=8eb6a981-75f1-4884-ab4e-46493d95bd20, " +
                        "name=setName, " +
                        "description=setDescription, " +
                        "imagePath=setImagePath, " +
                        "cityUuid=30579ab1-1e0a-4f4e-b7c7-7be44abbb79e, " +
                        "travelUuid=55420be5-07fe-4201-930e-03087fcf3f5b, " +
                        "userUuid=0bd93575-6c8b-4267-aa57-4d60c5788af6" +
                        ")";
        CityPlace cityPlace = new CityPlace();
        cityPlace.setUuid(UUID.fromString("8eb6a981-75f1-4884-ab4e-46493d95bd20"));
        cityPlace.setName("setName");
        cityPlace.setDescription("setDescription");
        cityPlace.setImagePath("setImagePath");
        cityPlace.setCityUuid(UUID.fromString("30579ab1-1e0a-4f4e-b7c7-7be44abbb79e"));
        cityPlace.setTravelUuid(UUID.fromString("55420be5-07fe-4201-930e-03087fcf3f5b"));
        cityPlace.setUserUuid(UUID.fromString("0bd93575-6c8b-4267-aa57-4d60c5788af6"));
        assertEquals(string, cityPlace.toString());
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(CityPlace cityPlace1, CityPlace cityPlace2) {
        assertNotEquals(null, cityPlace1);
        assertNotEquals("", cityPlace1);
        assertEquals(cityPlace1, cityPlace2);
        assertEquals(cityPlace2, cityPlace1);
    }

    private void checkBothNotEquals(CityPlace cityPlace1, CityPlace cityPlace2) {
        assertNotEquals(cityPlace1, cityPlace2);
        assertNotEquals(cityPlace2, cityPlace1);
    }
}