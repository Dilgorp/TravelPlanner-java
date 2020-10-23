package ru.dilgorp.java.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PlaceController {

    private final String GET_PLACES_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/places/all";
    private final String ADD_PLACES_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/places/add";
    private final String DELETE_PLACE_PATH =
            "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/places/{place_uuid}/delete";

    private final String GET_PLACE_IMAGE_PATH =
            "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/places/{place_uuid}/photo";

    private final CityPlaceRepository cityPlaceRepository;
    private final FileService fileService;

    @Autowired
    public PlaceController(CityPlaceRepository cityPlaceRepository, FileService fileService) {
        this.cityPlaceRepository = cityPlaceRepository;
        this.fileService = fileService;
    }

    @RequestMapping(value = GET_PLACES_PATH, method = RequestMethod.GET)
    public Response<List<CityPlace>> getPlaces(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityPlaceRepository.findPlacesByTravelUuidAndCityUuidAndUserUuid(travelUuid, cityUuid, userUuid)
        );
    }

    @RequestMapping(value = ADD_PLACES_PATH, method = RequestMethod.POST)
    public Response<List<CityPlace>> addPlaces(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @RequestBody List<Place> places
    ) {
        cityPlaceRepository.deleteCityPlaces(travelUuid, cityUuid, userUuid);

        List<CityPlace> cityPlaces = new ArrayList<>();
        for (Place place : places) {
            cityPlaces.add(
                    new CityPlace(
                            place.getName(),
                            place.getDescription(),
                            place.getImagePath(),
                            cityUuid,
                            travelUuid,
                            userUuid)
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
    public Response<List<CityPlace>> deletePlace(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @PathVariable("place_uuid") UUID placeUuid
    ) {
        cityPlaceRepository.deletePlace(placeUuid, travelUuid, cityUuid, userUuid);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityPlaceRepository.findPlacesByTravelUuidAndCityUuidAndUserUuid(travelUuid, cityUuid, userUuid)
        );
    }

    @RequestMapping(value = GET_PLACE_IMAGE_PATH, method = RequestMethod.GET)
    public byte[] getCityPlacePhoto(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @PathVariable("place_uuid") UUID placeUuid
    ) {
        CityPlace cityPlace = cityPlaceRepository.findByUuidAndTravelUuidAndCityUuidAndUserUuid(
                placeUuid, travelUuid, cityUuid, userUuid
        );
        if (cityPlace == null) {
            return null;
        }
        return fileService.getBytes(cityPlace.getImagePath());
    }
}
