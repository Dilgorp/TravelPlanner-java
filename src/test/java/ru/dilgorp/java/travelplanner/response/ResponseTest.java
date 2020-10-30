package ru.dilgorp.java.travelplanner.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    void constructorIsCorrect(){
        // when
        Response<String> response = new Response<>(ResponseType.SUCCESS, null, "Some data");

        // then
        assertNotNull(response);
    }

    @Test
    void messageIsCorrect(){
        // when
        Response<String> response = new Response<>(ResponseType.SUCCESS, null, "Some data");

        // then
        assertEquals("", response.getMessage());
    }

    @Test
    void dataIsCorrect(){
        // when
        Response<String> response = new Response<>(ResponseType.SUCCESS, null, "Some data");

        // then
        assertEquals("Some data", response.getData());
    }

    @Test
    void typeIsCorrect(){
        // when
        Response<String> response = new Response<>(ResponseType.ERROR, null, "Some data");

        // then
        assertEquals(ResponseType.ERROR, response.getType());
    }
}