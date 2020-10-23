package ru.dilgorp.java.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManager;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;
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
    private final String SET_CITY_NUMBER_PATH =
            "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/number/set/{number}";

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
        List<City> cities = cityRepository.findByTravelUuidAndUserUuidOrderByTravelNumber(travelUuid, userUuid);
        int travelNumber = 0;
        if (cities.size() > 0) {
            travelNumber = cities.get(cities.size() - 1).getTravelNumber();
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
                        cityUuid,
                        travelUuid,
                        userUuid
                )
        );
    }

    @RequestMapping(value = DELETE_CITY_PATH, method = RequestMethod.DELETE)
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

    @RequestMapping(value = SET_CITY_NUMBER_PATH, method = RequestMethod.POST)
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
