package ru.dilgorp.java.travelplanner.controller;

import lombok.RequiredArgsConstructor;
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
@RequestMapping("/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/places")
@RequiredArgsConstructor
public class PlaceController {

    private final CityPlaceRepository cityPlaceRepository;
    private final FileService fileService;

    @GetMapping("/all")
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

    @PostMapping("/add")
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

    @DeleteMapping("/{place_uuid}/delete")
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

    @GetMapping("/{place_uuid}/photo")
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
            return new byte[]{};
        }
        return fileService.getBytes(cityPlace.getImagePath());
    }
}
