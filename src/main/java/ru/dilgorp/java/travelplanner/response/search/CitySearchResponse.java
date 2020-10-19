package ru.dilgorp.java.travelplanner.response.search;

import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

public class CitySearchResponse extends Response {
    private final UserRequest userRequest;

    public CitySearchResponse(ResponseType type, String message, UserRequest userRequest) {
        super(type, message);
        this.userRequest = userRequest;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }
}
