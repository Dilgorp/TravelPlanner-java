package ru.dilgorp.java.travelplanner.controller;

import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.response.ResponseType;
import ru.dilgorp.java.travelplanner.response.search.CitySearchResponse;
import ru.dilgorp.java.travelplanner.response.search.PlaceSearchResponse;
import ru.dilgorp.java.travelplanner.task.search.LoadCityInfoTask;
import ru.dilgorp.java.travelplanner.task.search.LoadPlacesTask;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
public class SearchController {

    private static final String SEARCH_CITY_PATH = "/search/city/{cityname}";
    public static final String SEARCH_CITY_PHOTOS_PATH = "/search/photo/city/";
    private static final String SEARCH_CITY_PHOTO_PATH = SEARCH_CITY_PHOTOS_PATH + "{uuid}";
    private static final String SEARCH_CITY_PLACES_PATH = "/search/places/city/{uuid}";
    public static final String SEARCH_PLACES_PHOTOS_PATH = "/search/places/photo/";
    private static final String SEARCH_PLACES_PHOTO_PATH = SEARCH_PLACES_PHOTOS_PATH + "{uuid}";

    public static final String ASYNC_TASK_EXECUTOR_NAME = "ru.dilgorp.java.travelplanner.loadAsyncTaskExecutor";

    private final SearchTaskOptions searchTaskOptions;
    private final SyncTaskExecutor syncTaskExecutor;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchController(
            SearchTaskOptions searchTaskOptions,
            SyncTaskExecutor syncTaskExecutor,
            ThreadPoolTaskExecutor threadPoolTaskExecutor
    ) {
        this.searchTaskOptions = searchTaskOptions;
        this.syncTaskExecutor = syncTaskExecutor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @RequestMapping(value = SEARCH_CITY_PATH, method = RequestMethod.GET)
    public CitySearchResponse getCityInfo(@PathVariable("cityname") String cityName) {
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

        CitySearchResponse response;
        if (userRequestFromDB == null) {
            response = new CitySearchResponse(ResponseType.ERROR, "Город не найден", null);
        } else {
            if (userRequestFromDB.getExpired().compareTo(new Date()) <= 0) {
                threadPoolTaskExecutor.execute(task);
            }
            response = new CitySearchResponse(ResponseType.SUCCESS, "", userRequestFromDB);
        }

        return response;
    }

    @RequestMapping(value = SEARCH_CITY_PHOTO_PATH, method = RequestMethod.GET)
    public byte[] getCityPhoto(@PathVariable("uuid") UUID uuid) {
        return ControllerUtils.getImageBytes(
                searchTaskOptions.getUserRequestRepository()
                        .getOne(uuid)
                        .getImagePath()
        );
    }

    @RequestMapping(value = SEARCH_CITY_PLACES_PATH, method = RequestMethod.GET)
    public PlaceSearchResponse getCityPlaces(@PathVariable("uuid") UUID uuid) {

        List<Place> places =
                searchTaskOptions.getPlaceRepository()
                        .findByUserRequestUUID(uuid);

        if (places.size() == 0) {
            loadPlaces(uuid);

            places = searchTaskOptions.getPlaceRepository()
                    .findByUserRequestUUID(uuid);
        }

        PlaceSearchResponse response;
        if (places.size() == 0) {
            response = new PlaceSearchResponse(
                    ResponseType.ERROR,
                    "Интересные места не найдены",
                    places
            );
        } else {
            response = new PlaceSearchResponse(
                    ResponseType.SUCCESS,
                    "",
                    places
            );
        }

        return response;
    }

    @RequestMapping(value = SEARCH_PLACES_PHOTO_PATH, method = RequestMethod.GET)
    public byte[] getPlacePhoto(@PathVariable("uuid") UUID uuid) {
        return ControllerUtils.getImageBytes(
                searchTaskOptions.getPlaceRepository()
                        .getOne(uuid)
                        .getImagePath()
        );
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
