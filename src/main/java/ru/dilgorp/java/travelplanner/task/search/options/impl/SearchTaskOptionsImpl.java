package ru.dilgorp.java.travelplanner.task.search.options.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiService;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchTaskOptionsImpl implements SearchTaskOptions {

    @Getter
    private final String language;
    @Getter
    private final String photosFolder;
    @Getter
    private final int expiredDays;
    @Getter
    private final int placesCount;
    @Getter
    private final UserRequestRepository userRequestRepository;
    @Getter
    private final PlaceRepository placeRepository;
    @Getter
    private final GooglePlaceApiService placeApiService;
    @Getter
    private final FileService fileService;

    public static class Builder implements SearchTaskOptions.Builder {

        private String language;
        private String photosFolder;
        private int expiredDays;
        private int placesCount;
        private UserRequestRepository userRequestRepository;
        private PlaceRepository placeRepository;
        private GooglePlaceApiService placeApiService;
        private FileService fileService;

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
        public SearchTaskOptions.Builder setPlaceApiService(GooglePlaceApiService placeApiService) {
            this.placeApiService = placeApiService;
            return this;
        }

        @Override
        public SearchTaskOptions.Builder setFileService(FileService fileService) {
            this.fileService = fileService;
            return this;
        }

        @Override
        public SearchTaskOptions build() {
            return new SearchTaskOptionsImpl(
                    Objects.requireNonNull(language),
                    Objects.requireNonNull(photosFolder),
                    expiredDays,
                    placesCount,
                    Objects.requireNonNull(userRequestRepository),
                    Objects.requireNonNull(placeRepository),
                    Objects.requireNonNull(placeApiService),
                    Objects.requireNonNull(fileService)
            );
        }
    }
}
