package ru.dilgorp.java.travelplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManager;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/{user_uuid}/travel/{travel_uuid}/city")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;
    private final CityPlaceRepository cityPlaceRepository;
    private final DeletionManager deletionManager;
    private final FileService fileService;

    @GetMapping("/all")
    public Response<List<City>> getCities(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        cityRepository.clearEmptyTravelCities(travelUuid, userUuid);
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityRepository.findByTravelUuidAndUserUuidOrderByTravelNumber(travelUuid, userUuid)
        );
    }

    @PostMapping("/add")
    public Response<City> addCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        List<City> cities = cityRepository.findByTravelUuidAndUserUuidOrderByTravelNumber(travelUuid, userUuid);
        int travelNumber = 0;
        if (!cities.isEmpty()) {
            travelNumber = cities.get(cities.size() - 1).getTravelNumber() + 1;
        }
        City city = new City(
                "", 0, "", "",
                travelUuid, userUuid, travelNumber
        );

        cityRepository.save(city);
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                city
        );
    }

    @GetMapping("/{city_uuid}")
    public Response<City> getCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityRepository.findByUuidAndTravelUuidAndUserUuid(
                        cityUuid,
                        travelUuid,
                        userUuid
                )
        );
    }

    @PostMapping("/{city_uuid}/refresh")
    public Response<City> refreshCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        City city = cityRepository.findByUuidAndTravelUuidAndUserUuid(
                cityUuid,
                travelUuid,
                userUuid
        );

        city.setPlacesCount(
                cityPlaceRepository.findPlacesByTravelUuidAndCityUuidAndUserUuid(
                        travelUuid,
                        cityUuid,
                        userUuid
                ).size()
        );

        cityRepository.save(city);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                city
        );
    }

    @DeleteMapping("/{city_uuid}/delete")
    public Response<List<City>> deleteCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        deletionManager.delete(
                cityRepository.findByUuidAndTravelUuidAndUserUuid(
                        cityUuid,
                        travelUuid,
                        userUuid
                )
        );

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityRepository.findByTravelUuidAndUserUuidOrderByTravelNumber(travelUuid, userUuid)
        );
    }

    @GetMapping("/{city_uuid}/photo")
    public byte[] getCityPhoto(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        City city = cityRepository.findByUuidAndTravelUuidAndUserUuid(cityUuid, travelUuid, userUuid);
        if (city == null) {
            return new byte[]{};
        }
        return fileService.getBytes(city.getImagePath());
    }

    @PostMapping("/{city_uuid}/number/set/{number}")
    public Response<City> setCityNumber(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @PathVariable("number") int number
    ) {
        City city = cityRepository.findByUuidAndTravelUuidAndUserUuid(cityUuid, travelUuid, userUuid);
        if (city == null) {
            return new Response<>(
                    ResponseType.ERROR,
                    "Город не найден",
                    null
            );
        }

        city.setTravelNumber(number);
        cityRepository.save(city);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                city
        );
    }

}
