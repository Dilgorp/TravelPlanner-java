package ru.dilgorp.java.travelplanner.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiService;
import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiServiceImpl;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
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
    private final FileService fileService;

    @Autowired
    public TaskExecutorsConfig(UserRequestRepository userRequestRepository, PlaceRepository placeRepository, FileService fileService) {
        this.userRequestRepository = userRequestRepository;
        this.placeRepository = placeRepository;
        this.fileService = fileService;
    }

    @Bean
    public GooglePlaceApiService googlePlaceApiService(){
        GeoApiContext context = new GeoApiContext.Builder().apiKey(googlePlaceApiKey).build();
        return new GooglePlaceApiServiceImpl(context);
    }

    @Bean
    public SearchTaskOptions searchTaskOptions() {
        return new SearchTaskOptionsImpl.Builder()
                .setLanguage(googlePlaceLanguage)
                .setPhotosFolder(photosFolder)
                .setExpiredDays(expiredDays)
                .setPlacesCount(placesMaxCount)
                .setUserRequestRepository(userRequestRepository)
                .setPlaceRepository(placeRepository)
                .setPlaceApiService(googlePlaceApiService())
                .setFileService(fileService)
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
