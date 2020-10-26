package ru.dilgorp.java.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManager;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/{user_uuid}/travel")
public class TravelController {

    private final DeletionManager deletionManager;
    private final TravelRepository travelRepository;
    private final CityRepository cityRepository;
    private final CityPlaceRepository cityPlaceRepository;
    private final FileService fileService;

    @Autowired
    public TravelController(
            DeletionManager deletionManager,
            TravelRepository travelRepository,
            CityRepository cityRepository,
            CityPlaceRepository cityPlaceRepository,
            FileService fileService) {

        this.deletionManager = deletionManager;
        this.travelRepository = travelRepository;
        this.cityRepository = cityRepository;
        this.cityPlaceRepository = cityPlaceRepository;
        this.fileService = fileService;
    }

    @PostMapping("/add")
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

    @GetMapping("/{travel_uuid}")
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

    @DeleteMapping("/{travel_uuid}/delete")
    public Response<List<Travel>> deleteTravel(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid
    ) {
        Travel travel = travelRepository.getOne(travelUuid);
        deletionManager.delete(travel);
        return new Response<>(ResponseType.SUCCESS, "", travelRepository.findByUserUuid(userUuid));
    }

    @PostMapping("/{travel_uuid}/refresh")
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
            travelName = cities.get(0).getName() + " - ... - " + cities.get(cities.size() - 1).getName();
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

    @GetMapping("/all")
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

    @GetMapping("/{travel_uuid}/photo")
    public byte[] getTravelImage(
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("user_uuid") UUID userUuid
    ) {
        Travel travel = travelRepository.findByUuidAndUserUuid(travelUuid, userUuid);
        if (travel == null) {
            return new byte[]{};
        }
        return fileService.getBytes(travel.getImagePath());
    }
}
