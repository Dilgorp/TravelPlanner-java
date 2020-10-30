package ru.dilgorp.java.travelplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiService;
import ru.dilgorp.java.travelplanner.domain.DataGenerator;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.file.FileServiceTestImpl;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepositoryTestImpl;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepositoryTestImpl;
import ru.dilgorp.java.travelplanner.service.GooglePlaceApiServiceTestImpl;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;
import ru.dilgorp.java.travelplanner.task.search.options.impl.SearchTaskOptionsImpl;

@Configuration
public class TestConfig {
    @Bean
    public String googlePlaceApiKey() {
        return "";
    }

    @Bean
    public String googlePlaceLanguage() {
        return "ru";
    }

    @Bean
    public String photosFolder() {
        return "D:\\test";
    }

    @Bean
    public int expiredDays() {
        return 2;
    }

    @Bean
    public int placesMaxCount() {
        return 10;
    }

    @Bean
    public UserRequestRepository userRequestRepository() {
        return new UserRequestRepositoryTestImpl();
    }

    @Bean
    public PlaceRepository placeRepository() {
        return new PlaceRepositoryTestImpl();
    }

    @Bean
    public FileService fileService() {
        return new FileServiceTestImpl();
    }

    @Bean
    public DataGenerator dataGenerator(){
        return new DataGenerator();
    }

    @Bean
    public GooglePlaceApiService googlePlaceApiService(){
        return new GooglePlaceApiServiceTestImpl();
    }

    @Bean
    public SearchTaskOptions searchTaskOptions(){
        return new SearchTaskOptionsImpl.Builder()
                .setLanguage(googlePlaceLanguage())
                .setPhotosFolder(photosFolder())
                .setExpiredDays(expiredDays())
                .setPlacesCount(placesMaxCount())
                .setUserRequestRepository(userRequestRepository())
                .setPlaceRepository(placeRepository())
                .setPlaceApiService(googlePlaceApiService())
                .setFileService(fileService())
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
