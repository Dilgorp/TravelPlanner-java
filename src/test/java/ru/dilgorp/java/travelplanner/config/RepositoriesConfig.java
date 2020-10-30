package ru.dilgorp.java.travelplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManager;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManagerImpl;
import ru.dilgorp.java.travelplanner.repository.*;

@Configuration
public class RepositoriesConfig {
    @Bean
    public CityPlaceRepository cityPlaceRepository() {
        return new CityPlaceRepositoryTestImpl();
    }

    @Bean
    public CityRepository cityRepository() {
        return new CityRepositoryTestImpl();
    }

    @Bean
    public TravelRepository travelRepository() {
        return new TravelRepositoryTestImpl();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryTestImpl();
    }

    @Bean
    public DeletionManager deletionManager(){
        return new DeletionManagerImpl(
                travelRepository(),
                cityRepository(),
                cityPlaceRepository()
        );
    }
}
