package ru.dilgorp.java.travelplanner.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
public class CityController {

    private final String GET_CITIES_PATH = "/travel/{travel_uuid}/city/all";
    private final String ADD_CITY_PATH = "/travel/{travel_uuid}/city/add";
    private final String GET_CITY_PATH = "/travel/{travel_uuid}/city/{city_uuid}";
    private final String REFRESH_CITY_PATH = "/travel/{travel_uuid}/city/{city_uuid}/refresh";
    private final String DELETE_CITY_PATH = "/travel/{travel_uuid}/city/{city_uuid}/delete";

    private final CityRepository cityRepository;
    private final CityPlaceRepository cityPlaceRepository;

    public CityController(CityRepository cityRepository, CityPlaceRepository cityPlaceRepository) {
        this.cityRepository = cityRepository;
        this.cityPlaceRepository = cityPlaceRepository;
    }

    @RequestMapping(value = GET_CITIES_PATH, method = RequestMethod.GET)
    public Response<List<City>> getCities(@PathVariable("travel_uuid") UUID travelUuid) {
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                cityRepository.findByTravelUuid(travelUuid)
        );
    }

    @RequestMapping(value = ADD_CITY_PATH, method = RequestMethod.GET)
    public Response<City> addCity(@PathVariable("travel_uuid") UUID travelUuid) {
        City city = new City("New city", 0, "", "", travelUuid);
        cityRepository.save(city);
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                city
        );
    }
}
