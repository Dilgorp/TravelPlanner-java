package ru.dilgorp.java.travelplanner.response;

import ru.dilgorp.java.travelplanner.domain.Travel;

public class TravelResponse extends Response{

    private final Travel travel;
    public TravelResponse(ResponseType type, String message, Travel travel) {
        super(type, message);
        this.travel = travel;
    }

    public Travel getTravel() {
        return travel;
    }
}
