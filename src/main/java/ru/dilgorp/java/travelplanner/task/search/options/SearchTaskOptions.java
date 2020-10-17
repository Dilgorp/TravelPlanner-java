package ru.dilgorp.java.travelplanner.task.search.options;

import com.google.maps.GeoApiContext;
import ru.dilgorp.java.travelplanner.repository.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.UserRequestRepository;

public interface SearchTaskOptions {
    String getApiKey();
    String getLanguage();
    String getPhotosFolder();
    int getExpiredDays();
    int getPlacesCount();

    UserRequestRepository getUserRequestRepository();
    PlaceRepository getPlaceRepository();

    GeoApiContext getContext();

    interface Builder{
        Builder setApiKey(String apiKey);
        Builder setLanguage(String language);
        Builder setPhotosFolder(String photosFolder);
        Builder setExpiredDays(int expiredDays);
        Builder setPlacesCount(int placesCount);

        Builder setUserRequestRepository(UserRequestRepository userRequestRepository);
        Builder setPlaceRepository(PlaceRepository placeRepository);

        Builder setContext(GeoApiContext context);

        SearchTaskOptions build();
    }
}
