package ru.dilgorp.java.travelplanner.response;

import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;

public class CitySearchResponse extends Response{
    private final UserRequest userRequest;

    public CitySearchResponse(ResponseType type, String message, UserRequest userRequest) {
        super(type, message);
        this.userRequest = userRequest;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }
}
