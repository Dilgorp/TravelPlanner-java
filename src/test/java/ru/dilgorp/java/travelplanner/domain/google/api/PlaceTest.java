package ru.dilgorp.java.travelplanner.domain.google.api;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PlaceTest {

    @Test
    void equalsIsCorrect(){

        Place place1 = new Place();
        Place place2 = new Place();
        assertEquals(place1, place2);

        place1.setUuid(UUID.randomUUID());
        checkBothNotEquals(place1, place2);

        place2.setUuid(UUID.randomUUID());
        checkBothNotEquals(place1, place2);

        place2.setUuid(place1.getUuid());
        assertEquals(place1, place2);


        place1.setImagePath("setImagePath");
        checkBothNotEquals(place1, place2);

        place2.setImagePath("place1.getImagePath()");
        checkBothNotEquals(place1, place2);

        place2.setImagePath(place1.getImagePath());
        assertEquals(place1, place2);


        place1.setPlaceId("setPlaceId");
        checkBothNotEquals(place1, place2);

        place2.setPlaceId("place1.getPlaceId()");
        checkBothNotEquals(place1, place2);

        place2.setPlaceId(place1.getPlaceId());
        assertEquals(place1, place2);


        place1.setNextPageToken("setNextPageToken");
        checkBothNotEquals(place1, place2);

        place2.setNextPageToken("place1.getNextPageToken()");
        checkBothNotEquals(place1, place2);

        place2.setNextPageToken(place1.getNextPageToken());
        assertEquals(place1, place2);


        place1.setDescription("setDescription");
        checkBothNotEquals(place1, place2);

        place2.setDescription("place1.getDescription()");
        checkBothNotEquals(place1, place2);

        place2.setDescription(place1.getDescription());
        assertEquals(place1, place2);


        place1.setCurrentPageToken("setCurrentPageToken");
        checkBothNotEquals(place1, place2);

        place2.setCurrentPageToken("place1.getCurrentPageToken()");
        checkBothNotEquals(place1, place2);

        place2.setCurrentPageToken(place1.getCurrentPageToken());
        assertEquals(place1, place2);


        place1.setUserRequestUuid(UUID.randomUUID());
        checkBothNotEquals(place1, place2);

        place2.setUserRequestUuid(UUID.randomUUID());
        checkBothNotEquals(place1, place2);

        place2.setUserRequestUuid(place1.getUserRequestUuid());
        assertEquals(place1, place2);


        place1.setName("setName");
        checkBothNotEquals(place1, place2);

        place2.setName("place1.getName()");
        checkBothNotEquals(place1, place2);

        place2.setName(place1.getName());

        //then
        finalCheckForEquals(place1, place2);
    }

    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
    private void finalCheckForEquals(Place place1, Place place2) {
        assertNotEquals(null, place1);
        assertNotEquals("", place1);
        assertEquals(place1, place2);
        assertEquals(place1, place1);
    }

    private void checkBothNotEquals(Place place1, Place place2) {
        assertNotEquals(place1, place2);
        assertNotEquals(place2, place1);
    }

    @Test
    void hashcodeIsCorrect(){
        Place place1 = new Place();
        Place place2 = new Place();
        assertEquals(place1.hashCode(), place2.hashCode());

        place1.setUuid(UUID.randomUUID());
        place2.setUuid(place1.getUuid());


        place1.setImagePath("setImagePath");
        place2.setImagePath(place1.getImagePath());


        place1.setPlaceId("setPlaceId");
        place2.setPlaceId(place1.getPlaceId());


        place1.setNextPageToken("setNextPageToken");
        place2.setNextPageToken(place1.getNextPageToken());


        place1.setDescription("setDescription");
        place2.setDescription(place1.getDescription());


        place1.setCurrentPageToken("setCurrentPageToken");
        place2.setCurrentPageToken(place1.getCurrentPageToken());


        place1.setUserRequestUuid(UUID.randomUUID());
        place2.setUserRequestUuid(place1.getUserRequestUuid());

        place1.setName("setName");
        place2.setName(place1.getName());

        assertEquals(place1.hashCode(), place2.hashCode());
    }

    @Test
    void toStringIsCorrect(){
        String string =
                "Place(" +
                        "uuid=236506cb-6def-428a-b41e-ca1d681a7f44, " +
                        "name=setName, " +
                        "description=setDescription, " +
                        "placeId=setPlaceId, " +
                        "imagePath=setImagePath, " +
                        "currentPageToken=setCurrentPageToken, " +
                        "nextPageToken=setNextPageToken, " +
                        "userRequestUuid=a6d94a13-ff49-41b6-808f-03bdd54d32d5" +
                        ")";
        Place place1 = new Place();

        place1.setUuid(UUID.fromString("236506cb-6def-428a-b41e-ca1d681a7f44"));
        place1.setImagePath("setImagePath");
        place1.setPlaceId("setPlaceId");
        place1.setNextPageToken("setNextPageToken");
        place1.setDescription("setDescription");
        place1.setCurrentPageToken("setCurrentPageToken");
        place1.setUserRequestUuid(UUID.fromString("a6d94a13-ff49-41b6-808f-03bdd54d32d5"));
        place1.setName("setName");
        assertEquals(string, place1.toString());
    }
}