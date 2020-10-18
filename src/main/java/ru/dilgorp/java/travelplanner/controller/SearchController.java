package ru.dilgorp.java.travelplanner.controller;

import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.response.CitySearchResponse;
import ru.dilgorp.java.travelplanner.response.ResponseType;
import ru.dilgorp.java.travelplanner.task.search.LoadCityInfoTask;
import ru.dilgorp.java.travelplanner.task.search.LoadPlacesTask;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
public class SearchController {

    private static final String SEARCH_CITY_PATH = "/search/city/{cityname}";
    public static final String SEARCH_CITY_PHOTOS_PATH = "/search/photo/city/";
    private static final String SEARCH_CITY_PHOTO_PATH = SEARCH_CITY_PHOTOS_PATH + "{uuid}";
    public static final String SEARCH_CITY_PLACES_PATH = "/search/places/city/";
    private static final String SEARCH_CITY_PLACES_BY_UUID_PATH = SEARCH_CITY_PLACES_PATH + "{uuid}";

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
    public byte[] getCityPhoto(@PathVariable("uuid") UUID uuid) throws IOException {
        UserRequest requestFromDB = searchTaskOptions.getUserRequestRepository().getOne(uuid);

        InputStream inputStream = new FileInputStream(requestFromDB.getImagePath());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(inputStream.readAllBytes());

        return outputStream.toByteArray();
    }

    @RequestMapping(value = SEARCH_CITY_PLACES_BY_UUID_PATH, method = RequestMethod.GET)
    public List<Place> getCityPlaces(@PathVariable("uuid") UUID uuid, @RequestParam(defaultValue = "") String pageToken) {

        List<Place> places =
                searchTaskOptions.getPlaceRepository()
                        .findByUserRequestUUIDAndCurrentPageToken(uuid, pageToken);

        if(places.size() == 0){
            LoadPlacesTask loadPlacesTask =
                    new LoadPlacesTask(
                            uuid,
                            pageToken,
                            searchTaskOptions,
                            false
                    );
            syncTaskExecutor.execute(loadPlacesTask);
        }

        LoadPlacesTask loadPlacesTask =
                new LoadPlacesTask(
                        uuid,
                        pageToken,
                        searchTaskOptions,
                        true
                );

        threadPoolTaskExecutor.execute(loadPlacesTask);

        return searchTaskOptions.getPlaceRepository()
                .findByUserRequestUUIDAndCurrentPageToken(uuid, pageToken);
    }
}
