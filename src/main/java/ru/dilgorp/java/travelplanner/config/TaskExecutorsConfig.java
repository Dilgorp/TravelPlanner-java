package ru.dilgorp.java.travelplanner.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.dilgorp.java.travelplanner.repository.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;
import ru.dilgorp.java.travelplanner.task.search.options.impl.SearchTaskOptionsImpl;

@Configuration
public class TaskExecutorsConfig {

    @Value("${google.place-api.key}")
    private String googlePlaceApiKey;

    @Value("${google.place-api.language}")
    private String googlePlaceLanguage;

    @Value("${city.photos-folder}")
    private String photosFolder;

    @Value("${city.expired-days}")
    private int expiredDays;

    @Value("${places.max-count}")
    private int placesMaxCount;

    private final UserRequestRepository userRequestRepository;
    private final PlaceRepository placeRepository;

    public TaskExecutorsConfig(UserRequestRepository userRequestRepository, PlaceRepository placeRepository) {
        this.userRequestRepository = userRequestRepository;
        this.placeRepository = placeRepository;
    }

    @Bean
    public SearchTaskOptions searchTaskOptions() {
        return new SearchTaskOptionsImpl.Builder()
                .setApiKey(googlePlaceApiKey)
                .setLanguage(googlePlaceLanguage)
                .setPhotosFolder(photosFolder)
                .setExpiredDays(expiredDays)
                .setPlacesCount(placesMaxCount)
                .setUserRequestRepository(userRequestRepository)
                .setPlaceRepository(placeRepository)
                .setContext(new GeoApiContext.Builder().apiKey(googlePlaceApiKey).build())
                .build();
    }

    @Bean
    public SyncTaskExecutor syncTaskExecutor() {
        return new SyncTaskExecutor();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
