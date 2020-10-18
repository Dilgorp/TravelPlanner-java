package ru.dilgorp.java.travelplanner.task.search;

import com.google.maps.ImageResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiService;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoadPlacesTask implements Runnable {

    private static final String SEARCH_SUFFIX_PLACE_BY_CITY_NAME = "+city+point+of+interest";

    private final UUID requestUUID;
    private final String pageToken;
    private final SearchTaskOptions searchTaskOptions;
    private final boolean fullLoading;
    private String nextPageToken = null;

    public LoadPlacesTask(
            UUID requestUUID,
            String pageToken,
            SearchTaskOptions searchTaskOptions,
            boolean fullLoading) {
        this.requestUUID = requestUUID;
        this.pageToken = pageToken;
        this.searchTaskOptions = searchTaskOptions;
        this.fullLoading = fullLoading;
    }

    @Override
    public void run() {
        loadPlaces();
        if (fullLoading && nextPageToken != null && !nextPageToken.isEmpty()) {
            LoadPlacesTask loadPlacesTask = new LoadPlacesTask(
                    requestUUID, nextPageToken, searchTaskOptions, true
            );
            loadPlacesTask.run();
        }
    }

    private void loadPlaces() {
        PlaceRepository placeRepository = searchTaskOptions.getPlaceRepository();
        UserRequestRepository userRequestRepository = searchTaskOptions.getUserRequestRepository();
        int placesDBCount = placeRepository.findCountByUserRequestUUID(requestUUID);
        if (placesDBCount >= searchTaskOptions.getPlacesCount()) {
            return;
        }

        GooglePlaceApiService apiService = searchTaskOptions.getPlaceApiService();

        Optional<UserRequest> byId = userRequestRepository.findById(requestUUID);
        if (!byId.isPresent()) {
            return;
        }

        UserRequest requestFromDB = byId.get();

        String query = requestFromDB.getName().replace(' ', '+') + SEARCH_SUFFIX_PLACE_BY_CITY_NAME;

        try {
            PlacesSearchResponse places = apiService.getPlaces(query, searchTaskOptions.getLanguage(), pageToken);
            nextPageToken = places.nextPageToken;

            List<Place> placeList = new ArrayList<>();
            for (PlacesSearchResult result : places.results) {
                if (result.photos == null || result.photos.length == 0) {
                    continue;
                }

                Place place = new Place();
                fillPlace(place, result);
                placeList.add(place);
            }

            placeRepository.saveAll(placeList);
            loadImages();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImages() throws InterruptedException, ApiException, IOException {
        GooglePlaceApiService apiService = searchTaskOptions.getPlaceApiService();
        List<Place> places =
                searchTaskOptions.getPlaceRepository()
                        .findByUserRequestUUIDAndCurrentPageToken(requestUUID, pageToken);

        for (Place place : places) {
            ImageResult imageResult =
                    apiService.getImageByPlaceId(place.getPlaceId(), searchTaskOptions.getLanguage());

            if (imageResult == null) {
                continue;
            }

            String filePath = LoadTasksUtils.createImagePath(
                    requestUUID,
                    place.getUuid().toString(),
                    imageResult,
                    searchTaskOptions.getPhotosFolder()
            );

            LoadTasksUtils.saveImage(imageResult, filePath);
            place.setImagePath(filePath);
        }

        searchTaskOptions.getPlaceRepository().saveAll(places);
    }

    private void fillPlace(
            Place place,
            PlacesSearchResult result
    ) {
        place.setCurrentPageToken(pageToken);
        place.setDescription(result.formattedAddress);
        place.setName(result.name);
        place.setNextPageToken(nextPageToken);
        place.setPlaceId(result.placeId);
        place.setUserRequestUUID(requestUUID);
    }
}
