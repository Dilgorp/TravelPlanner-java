package ru.dilgorp.java.travelplanner.task.search;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;
import ru.dilgorp.java.travelplanner.domain.UserRequest;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.UUID;

public class LoadPlacesTask implements Runnable {

    private static final String SEARCH_SUFFIX_PLACE_BY_CITY_NAME = "+city+point+of+interest";

    private final UUID requestUUID;
    private final String pageToken;
    private final SearchTaskOptions searchTaskOptions;

    public LoadPlacesTask(
            UUID requestUUID,
            String pageToken,
            SearchTaskOptions searchTaskOptions) {
        this.requestUUID = requestUUID;
        this.pageToken = pageToken;
        this.searchTaskOptions = searchTaskOptions;
    }

    @Override
    public void run() {
        loadPlaces();
    }

    private void loadPlaces() {
        if (searchTaskOptions.getPlaceRepository().findCountByUserRequestUUID(requestUUID) >= 100) {
            return;
        }

        UserRequest requestFromDB = searchTaskOptions.getUserRequestRepository().getOne(requestUUID);

        try {
            PlacesSearchResponse places = PlacesApi.textSearchQuery(
                    searchTaskOptions.getContext(),
                    requestFromDB.getName().replace(' ', '+') + SEARCH_SUFFIX_PLACE_BY_CITY_NAME)
                    .language(searchTaskOptions.getLanguage())
                    .await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
