package ru.dilgorp.java.travelplanner.controller;

import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;
import ru.dilgorp.java.travelplanner.response.AllTravelResponse;
import ru.dilgorp.java.travelplanner.response.ResponseType;
import ru.dilgorp.java.travelplanner.response.TravelResponse;

import java.util.Optional;
import java.util.UUID;

@RestController
public class TravelController {
    private static final String ADD_TRAVEL_PATH = "/travel/add";
    private static final String GET_TRAVEL_PATH = "/travel/{uuid}";
    private static final String DELETE_TRAVEL_PATH = "/travel/{uuid}/delete";
    private static final String REFRESH_TRAVEL_PATH = "/travel/{uuid}/refresh";
    private static final String ALL_TRAVEL_PATH = "/travel/all";
    private static final String GET_TRAVEL_IMAGE_PATH = "/travel/{uuid}/photo";

    private final TravelRepository travelRepository;

    public TravelController(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @RequestMapping(value = ADD_TRAVEL_PATH, method = RequestMethod.GET)
    public TravelResponse addTravel() {
        Travel travel = new Travel("", 0, 0, "");
        travelRepository.save(travel);

        return new TravelResponse(
                ResponseType.SUCCESS,
                "",
                travel
        );
    }

    @RequestMapping(value = GET_TRAVEL_PATH, method = RequestMethod.GET)
    public TravelResponse getTravel(@PathVariable("uuid") UUID uuid) {
        Optional<Travel> byId = travelRepository.findById(uuid);
        TravelResponse response;
        response = byId.map(travel -> new TravelResponse(ResponseType.SUCCESS, "", travel))
                .orElseGet(() -> new TravelResponse(ResponseType.ERROR, "Путешествие не найдено", null));
        return response;
    }

    @RequestMapping(value = DELETE_TRAVEL_PATH, method = RequestMethod.GET)
    public TravelResponse deleteTravel(@PathVariable("uuid") UUID uuid) {
        Travel travel = travelRepository.getOne(uuid);
        travelRepository.delete(travel);
        return new TravelResponse(ResponseType.SUCCESS, "", null);
    }

    @RequestMapping(value = REFRESH_TRAVEL_PATH, method = RequestMethod.GET)
    public TravelResponse refreshTravel(@PathVariable("uuid") UUID uuid) {
        Optional<Travel> byId = travelRepository.findById(uuid);
        TravelResponse response;
        response = byId.map(travel -> new TravelResponse(ResponseType.SUCCESS, "", travel))
                .orElseGet(() -> new TravelResponse(ResponseType.ERROR, "Путешествие не найдено", null));
        return response;
    }

    @RequestMapping(value = ALL_TRAVEL_PATH, method = RequestMethod.GET)
    public AllTravelResponse allTravels() {
        return new AllTravelResponse(ResponseType.SUCCESS, "", travelRepository.findAll());
    }

    @RequestMapping(value = GET_TRAVEL_IMAGE_PATH, method = RequestMethod.GET)
    public byte[] getTravelImage(@PathVariable("uuid") UUID uuid){
        byte[] result = null;
        Optional<Travel> byId = travelRepository.findById(uuid);

        if(byId.isPresent()){
            result = ControllerUtils.getImageBytes(byId.get().getImagePath());
        }

        return result;
    }
}
