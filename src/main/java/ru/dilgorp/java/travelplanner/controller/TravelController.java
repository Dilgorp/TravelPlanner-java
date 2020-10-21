package ru.dilgorp.java.travelplanner.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManager;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
public class TravelController {
    private static final String ADD_TRAVEL_PATH = "/user/{user_uuid}/travel/add";
    private static final String GET_TRAVEL_PATH = "/user/{user_uuid}/travel/{travel_uuid}";
    private static final String DELETE_TRAVEL_PATH = "/user/{user_uuid}/travel/{travel_uuid}/delete";
    private static final String REFRESH_TRAVEL_PATH = "/user/{user_uuid}/travel/{travel_uuid}/refresh";
    private static final String ALL_TRAVEL_PATH = "/user/{user_uuid}/travel/all";
    private static final String GET_TRAVEL_IMAGE_PATH = "/user/{user_uuid}/travel/{travel_uuid}/photo";

    private final DeletionManager deletionManager;
    private final TravelRepository travelRepository;
    private final CityRepository cityRepository;
    private final CityPlaceRepository cityPlaceRepository;

    public TravelController(
            DeletionManager deletionManager,
            TravelRepository travelRepository,
            CityRepository cityRepository,
            CityPlaceRepository cityPlaceRepository
    ) {
        this.deletionManager = deletionManager;
        this.travelRepository = travelRepository;
        this.cityRepository = cityRepository;
        this.cityPlaceRepository = cityPlaceRepository;
    }

    @RequestMapping(value = ADD_TRAVEL_PATH, method = RequestMethod.POST)
    public Response<Travel> addTravel(
            @PathVariable("user_uuid") UUID userUuid
    ) {
        Travel travel = new Travel("", 0, 0, "", userUuid);
        travelRepository.save(travel);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                travel
        );
    }

    @RequestMapping(value = GET_TRAVEL_PATH, method = RequestMethod.GET)
    public Response<Travel> getTravel(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                travelRepository.findByUuidAndUserUuid(travelUuid, userUuid)
        );
    }

    @RequestMapping(value = DELETE_TRAVEL_PATH, method = RequestMethod.DELETE)
    public Response<Travel> deleteTravel(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        Travel travel = travelRepository.getOne(travelUuid);
        deletionManager.delete(travel);
        return new Response<>(ResponseType.SUCCESS, "", null);
    }

    @RequestMapping(value = REFRESH_TRAVEL_PATH, method = RequestMethod.POST)
    public Response<Travel> refreshTravel(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        Travel travel = travelRepository.findByUuidAndUserUuid(travelUuid, userUuid);
        List<City> cities = cityRepository.findByTravelUuidAndUserUuidOrderByTravelNumber(travelUuid, userUuid);

        String travelName;
        String travelImage;
        if (cities.size() == 1) {
            travelName = cities.get(0).getName();
            travelImage = cities.get(0).getImagePath();
        } else if (cities.size() == 2) {
            travelName = cities.get(0).getName() + " - " + cities.get(1).getName();
            travelImage = cities.get(0).getImagePath();
        } else if (cities.size() > 2) {
            travelName = cities.get(0).getName() + " - ... - " + cities.get(1).getName();
            travelImage = cities.get(0).getImagePath();
        } else {
            travelName = "";
            travelImage = "";
        }

        List<CityPlace> places = cityPlaceRepository.findPlacesByTravelUuidAndUserUuid(travelUuid, userUuid);

        travel.setName(travelName);
        travel.setImagePath(travelImage);
        travel.setCitiesCount(cities.size());
        travel.setPlacesCount(places.size());

        travelRepository.save(travel);

        return new Response<>(ResponseType.SUCCESS, "", travel);
    }

    @RequestMapping(value = ALL_TRAVEL_PATH, method = RequestMethod.GET)
    public Response<List<Travel>> allTravels(
            @PathVariable("user_uuid") UUID userUuid
    ) {
        deletionManager.clearEmptyTravels(userUuid);
        return new Response<>(
                ResponseType.SUCCESS,
                "",
                travelRepository.findByUserUuid(userUuid)
        );
    }

    @RequestMapping(value = GET_TRAVEL_IMAGE_PATH, method = RequestMethod.GET)
    public byte[] getTravelImage(
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("user_uuid") UUID userUuid
    ) {
        Travel travel = travelRepository.findByUuidAndUserUuid(travelUuid, userUuid);
        if(travel == null){
            return null;
        }
        return ControllerUtils.getImageBytes(travel.getImagePath());
    }
}
