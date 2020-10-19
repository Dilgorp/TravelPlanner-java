package ru.dilgorp.java.travelplanner.response;

import ru.dilgorp.java.travelplanner.domain.Travel;

import java.util.List;

public class AllTravelResponse extends Response {

    private final List<Travel> travels;
    public AllTravelResponse(ResponseType type, String message, List<Travel> travels) {
        super(type, message);
        this.travels = travels;
    }

    public List<Travel> getTravels() {
        return travels;
    }
}
