package ru.dilgorp.java.travelplanner.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.List;
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

    @RequestMapping(value = ADD_TRAVEL_PATH, method = RequestMethod.POST)
    public Response<Travel> addTravel() {
        Travel travel = new Travel("", 0, 0, "");
        travelRepository.save(travel);

        return new Response<>(
                ResponseType.SUCCESS,
                "",
                travel
        );
    }

    @RequestMapping(value = GET_TRAVEL_PATH, method = RequestMethod.GET)
    public Response<Travel> getTravel(@PathVariable("uuid") UUID uuid) {
        Optional<Travel> byId = travelRepository.findById(uuid);
        Response<Travel> response;
        response = byId.map(travel -> new Response<>(ResponseType.SUCCESS, "", travel))
                .orElseGet(() -> new Response<>(ResponseType.ERROR, "Путешествие не найдено", null));
        return response;
    }

    @RequestMapping(value = DELETE_TRAVEL_PATH, method = RequestMethod.DELETE)
    public Response<Travel> deleteTravel(@PathVariable("uuid") UUID uuid) {
        Travel travel = travelRepository.getOne(uuid);
        travelRepository.delete(travel);
        return new Response<>(ResponseType.SUCCESS, "", null);
    }

    @RequestMapping(value = REFRESH_TRAVEL_PATH, method = RequestMethod.POST)
    public Response<Travel> refreshTravel(@PathVariable("uuid") UUID uuid) {
        Optional<Travel> byId = travelRepository.findById(uuid);
        Response<Travel> response;
        response = byId.map(travel -> new Response<>(ResponseType.SUCCESS, "", travel))
                .orElseGet(() -> new Response<>(ResponseType.ERROR, "Путешествие не найдено", null));
        return response;
    }

    @RequestMapping(value = ALL_TRAVEL_PATH, method = RequestMethod.GET)
    public Response<List<Travel>> allTravels() {
        return new Response<>(ResponseType.SUCCESS, "", travelRepository.findAll());
    }

    @RequestMapping(value = GET_TRAVEL_IMAGE_PATH, method = RequestMethod.GET)
    public byte[] getTravelImage(@PathVariable("uuid") UUID uuid) {
        byte[] result = null;
        Optional<Travel> byId = travelRepository.findById(uuid);

        if (byId.isPresent()) {
            result = ControllerUtils.getImageBytes(byId.get().getImagePath());
        }

        return result;
    }
}
