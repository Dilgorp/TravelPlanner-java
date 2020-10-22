package ru.dilgorp.java.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManager;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
public class CityController {

    private final String GET_CITIES_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/all";
    private final String ADD_CITY_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/add";
    private final String GET_CITY_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}";
    private final String DELETE_CITY_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/delete";
    private final String GET_CITY_IMAGE_PATH = "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/photo";

    private final CityRepository cityRepository;
    private final DeletionManager deletionManager;
    private final FileService fileService;

    @Autowired
    public CityController(CityRepository cityRepository, DeletionManager deletionManager, FileService fileService) {
        this.cityRepository = cityRepository;
        this.deletionManager = deletionManager;
        this.fileService = fileService;
    }

    @RequestMapping(value = GET_CITIES_PATH, method = RequestMethod.GET)
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

    @RequestMapping(value = ADD_CITY_PATH, method = RequestMethod.POST)
    public Response<City> addCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        City city = new City(
                "", 0, "", "", travelUuid, userUuid,
                cityRepository.findByTravelUuidAndUserUuidOrderByTravelNumber(travelUuid, userUuid).size()
        );

        cityRepository.save(city);
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                city
        );
    }

    @RequestMapping(value = GET_CITY_PATH, method = RequestMethod.GET)
    public Response<City> getCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityRepository.findByUuidAndTravelUuidAndUserUuid(
                        travelUuid,
                        cityUuid,
                        userUuid
                )
        );
    }

    @RequestMapping(value = DELETE_CITY_PATH, method = RequestMethod.DELETE)
    public Response<City> deleteCity(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        deletionManager.delete(
                cityRepository.findByUuidAndTravelUuidAndUserUuid(
                        travelUuid,
                        cityUuid,
                        userUuid
                )
        );

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                null
        );
    }

    @RequestMapping(value = GET_CITY_IMAGE_PATH, method = RequestMethod.GET)
    public byte[] getCityPhoto(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid
    ) {
        City city = cityRepository.findByUuidAndTravelUuidAndUserUuid(cityUuid, travelUuid, userUuid);
        if (city == null) {
            return null;
        }
        return fileService.getBytes(city.getImagePath());
    }

}
