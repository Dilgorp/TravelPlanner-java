package ru.dilgorp.java.travelplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;
import ru.dilgorp.java.travelplanner.task.search.LoadCityInfoTask;
import ru.dilgorp.java.travelplanner.task.search.LoadPlacesTask;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
public class SearchController {

    private static final String SEARCH_CITY_PATH =
            "/user/{user_uuid}/travel/{travel_uuid}/city/{city_uuid}/search/{cityname}";

    private static final String SEARCH_CITY_PLACES_PATH = "/search/places/{request_uuid}";

    private static final String SEARCH_PLACES_PHOTO_PATH = "/search/places/photo/{request_uuid}";

    public static final String ASYNC_TASK_EXECUTOR_NAME = "ru.dilgorp.java.travelplanner.loadAsyncTaskExecutor";

    private final SearchTaskOptions searchTaskOptions;
    private final SyncTaskExecutor syncTaskExecutor;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final CityRepository cityRepository;

    @Autowired
    public SearchController(
            SearchTaskOptions searchTaskOptions,
            SyncTaskExecutor syncTaskExecutor,
            ThreadPoolTaskExecutor threadPoolTaskExecutor,
            CityRepository cityRepository) {

        this.searchTaskOptions = searchTaskOptions;
        this.syncTaskExecutor = syncTaskExecutor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.cityRepository = cityRepository;
    }

    @RequestMapping(value = SEARCH_CITY_PATH, method = RequestMethod.GET)
    public Response<City> getCityInfo(
            @PathVariable("user_uuid") UUID userUuid,
            @PathVariable("travel_uuid") UUID travelUuid,
            @PathVariable("city_uuid") UUID cityUuid,
            @PathVariable("cityname") String cityName
    ) {
        String textSearch = cityName.toLowerCase();
        UserRequestRepository userRequestRepository = searchTaskOptions.getUserRequestRepository();

        UserRequest userRequestFromDB = userRequestRepository.findByText(textSearch);
        LoadCityInfoTask task =
                new LoadCityInfoTask(
                        cityName,
                        userRequestFromDB,
                        searchTaskOptions
                );

        if (userRequestFromDB == null) {
            syncTaskExecutor.execute(task);
            userRequestFromDB = userRequestRepository.findByText(textSearch);
        }

        Response<City> response;
        if (userRequestFromDB == null) {
            response = new Response<>(ResponseType.ERROR, "Город не найден", null);
            return response;
        }

        City city = cityRepository.findByUuidAndTravelUuidAndUserUuid(cityUuid, travelUuid, userUuid);
        fillCity(userRequestFromDB, city);
        cityRepository.save(city);

        response = new Response<>(ResponseType.SUCCESS, "", city);

        if (userRequestFromDB.getExpired().compareTo(new Date()) <= 0) {
            threadPoolTaskExecutor.execute(task);
        }

        return response;
    }


    @RequestMapping(value = SEARCH_CITY_PLACES_PATH, method = RequestMethod.GET)
    public Response<List<Place>> getCityPlaces(
            @PathVariable("request_uuid") UUID requestUuid
    ) {

        List<Place> places =
                searchTaskOptions.getPlaceRepository()
                        .findByUserRequestUuid(requestUuid);

        if (places.size() == 0) {
            loadPlaces(requestUuid);

            places = searchTaskOptions.getPlaceRepository()
                    .findByUserRequestUuid(requestUuid);
        }

        Response<List<Place>> response;
        if (places.size() == 0) {
            response = new Response<>(
                    ResponseType.ERROR,
                    "Интересные места не найдены",
                    places
            );
        } else {
            response = new Response<>(
                    ResponseType.SUCCESS,
                    "",
                    places
            );
        }

        return response;
    }

    @RequestMapping(value = SEARCH_PLACES_PHOTO_PATH, method = RequestMethod.GET)
    public byte[] getPlacePhoto(@PathVariable("request_uuid") UUID requestUuid) {
        return searchTaskOptions.getFileService().getBytes(
                searchTaskOptions.getPlaceRepository()
                        .getOne(requestUuid)
                        .getImagePath()
        );
    }

    private void fillCity(UserRequest userRequestFromDB, City city) {
        city.setName(userRequestFromDB.getName());
        city.setDescription(userRequestFromDB.getFormattedAddress());
        city.setImagePath(userRequestFromDB.getImagePath());
        city.setUserRequestUuid(userRequestFromDB.getUuid());
    }

    private void loadPlaces(UUID uuid) {
        LoadPlacesTask loadPlacesTask =
                new LoadPlacesTask(
                        uuid,
                        "",
                        searchTaskOptions
                );
        syncTaskExecutor.execute(loadPlacesTask);
    }
}
