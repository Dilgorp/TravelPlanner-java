package ru.dilgorp.java.travelplanner.task.search.options.impl;

import com.google.maps.GeoApiContext;
import ru.dilgorp.java.travelplanner.repository.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.Objects;

public class SearchTaskOptionsImpl implements SearchTaskOptions {

    private final String apiKey;
    private final String language;
    private final String photosFolder;
    private final int expiredDays;
    private final int placesCount;

    private final UserRequestRepository userRequestRepository;
    private final PlaceRepository placeRepository;
    private final GeoApiContext context;

    public SearchTaskOptionsImpl(
            String apiKey,
            String language,
            String photosFolder,
            int expiredDays,
            int placesCount,
            UserRequestRepository userRequestRepository,
            PlaceRepository placeRepository,
            GeoApiContext context
    ) {
        this.apiKey = apiKey;
        this.language = language;
        this.photosFolder = photosFolder;
        this.expiredDays = expiredDays;
        this.placesCount = placesCount;
        this.userRequestRepository = userRequestRepository;
        this.placeRepository = placeRepository;
        this.context = context;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getPhotosFolder() {
        return photosFolder;
    }

    @Override
    public int getExpiredDays() {
        return expiredDays;
    }

    @Override
    public UserRequestRepository getUserRequestRepository() {
        return userRequestRepository;
    }

    @Override
    public PlaceRepository getPlaceRepository() {
        return placeRepository;
    }

    @Override
    public GeoApiContext getContext() {
        return context;
    }

    @Override
    public int getPlacesCount() {
        return placesCount;
    }

    public static class Builder implements SearchTaskOptions.Builder {

        private String apiKey;
        private String language;
        private String photosFolder;
        private int expiredDays;
        private int placesCount;
        private UserRequestRepository userRequestRepository;
        private PlaceRepository placeRepository;
        private GeoApiContext context;

        @Override
        public SearchTaskOptions.Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setPhotosFolder(String photosFolder) {
            this.photosFolder = photosFolder;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setExpiredDays(int expiredDays) {
            this.expiredDays = expiredDays;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setPlacesCount(int placesCount) {
            this.placesCount = placesCount;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setUserRequestRepository(UserRequestRepository userRequestRepository) {
            this.userRequestRepository = userRequestRepository;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setPlaceRepository(PlaceRepository placeRepository) {
            this.placeRepository = placeRepository;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setContext(GeoApiContext context) {
            this.context = context;
            return this;
        }

        @Override
        public SearchTaskOptions build() {
            return new SearchTaskOptionsImpl(
                    Objects.requireNonNull(apiKey),
                    Objects.requireNonNull(language),
                    Objects.requireNonNull(photosFolder),
                    expiredDays,
                    placesCount,
                    Objects.requireNonNull(userRequestRepository),
                    Objects.requireNonNull(placeRepository),
                    Objects.requireNonNull(context)
            );
        }
    }
}
