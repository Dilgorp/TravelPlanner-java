package ru.dilgorp.java.travelplanner.task.search.options;

import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiService;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;

public interface SearchTaskOptions {
    String getLanguage();
    String getPhotosFolder();
    int getExpiredDays();
    int getPlacesCount();

    UserRequestRepository getUserRequestRepository();
    PlaceRepository getPlaceRepository();

    GooglePlaceApiService getPlaceApiService();

    FileService getFileService();

    interface Builder{
        Builder setLanguage(String language);
        Builder setPhotosFolder(String photosFolder);
        Builder setExpiredDays(int expiredDays);
        Builder setPlacesCount(int placesCount);

        Builder setUserRequestRepository(UserRequestRepository userRequestRepository);
        Builder setPlaceRepository(PlaceRepository placeRepository);

        Builder setPlaceApiService(GooglePlaceApiService placeApiService);

        Builder setFileService(FileService fileService);

        SearchTaskOptions build();
    }
}
