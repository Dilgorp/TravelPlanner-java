package ru.dilgorp.java.travelplanner.controller;

import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PlaceController {

    private final String GET_PLACES_PATH = "/travel/{travel_uuid}/city/{city_uuid}/places/all";
    private final String ADD_PLACES_PATH = "/travel/{travel_uuid}/city/{city_uuid}/places/add";
    private final String DELETE_PLACE_PATH = "/travel/{travel_uuid}/city/{city_uuid}/places/{place_uuid}/delete";

    private final CityPlaceRepository cityPlaceRepository;

    public PlaceController(CityPlaceRepository cityPlaceRepository) {
        this.cityPlaceRepository = cityPlaceRepository;
    }

    @RequestMapping(value = GET_PLACES_PATH, method = RequestMethod.GET)
    public Response<List<CityPlace>> getPlaces(
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityPlaceRepository.findPlacesByTravelUuidAndCityUuid(travelUuid, cityUuid)
        );
    }

    @RequestMapping(value = ADD_PLACES_PATH, method = RequestMethod.POST)
    public Response<CityPlace> addPlaces(
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @RequestBody List<Place> places
    ) {
        cityPlaceRepository.deleteCityPlaces(travelUuid, cityUuid);

        List<CityPlace> cityPlaces = new ArrayList<>();
        for (Place place : places) {
            cityPlaces.add(
                    new CityPlace(
                            place.getName(),
                            place.getDescription(),
                            place.getImagePath(),
                            cityUuid,
                            travelUuid
                    )
            );
        }

        cityPlaceRepository.saveAll(cityPlaces);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                null
        );
    }

    @RequestMapping(value = DELETE_PLACE_PATH, method = RequestMethod.DELETE)
    public Response<CityPlace> deletePlace(
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @PathVariable("place_uuid") UUID placeUuid
    ) {
        cityPlaceRepository.deletePlace(placeUuid, travelUuid, cityUuid);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                null
        );
    }
}
