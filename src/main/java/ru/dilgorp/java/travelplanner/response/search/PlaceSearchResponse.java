package ru.dilgorp.java.travelplanner.response.search;

import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;

public class PlaceSearchResponse extends Response {
    private final List<Place> places;

    public PlaceSearchResponse(ResponseType type, String message, List<Place> places) {
        super(type, message);
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }
}
