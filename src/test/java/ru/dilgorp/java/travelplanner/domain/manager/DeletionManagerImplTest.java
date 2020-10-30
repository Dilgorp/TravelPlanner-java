package ru.dilgorp.java.travelplanner.domain.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(RepositoriesConfig.class)
class DeletionManagerImplTest {

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityPlaceRepository cityPlaceRepository;

    private DeletionManagerImpl deletionManager;

    @BeforeEach
    void beforeEach(){
        deletionManager = new DeletionManagerImpl(
                travelRepository,
                cityRepository,
                cityPlaceRepository
        );
    }

    @Test
    void constructorIsCorrect(){
        // then
        assertNotNull(deletionManager);
    }

    @Test
    void clearEmptyTravelsIsCorrect(){
        // given
        travelRepository.save(new Travel());
        travelRepository.save(new Travel());
        travelRepository.save(new Travel());

        // when
        deletionManager.clearEmptyTravels(UUID.randomUUID());

        // then
        assertEquals(0, travelRepository.findAll().size());
    }

    @Test
    void deleteCityIsCorrect(){
        // given
        City city = new City();
        cityPlaceRepository.save(new CityPlace());
        cityPlaceRepository.save(new CityPlace());
        cityPlaceRepository.save(new CityPlace());
        cityRepository.save(city);

        // when
        deletionManager.delete(city);

        // then
        assertEquals(0, cityRepository.findAll().size());
        assertEquals(0, cityPlaceRepository.findAll().size());
    }

    @Test
    void deleteTravelIsCorrect(){
        // given
        Travel travel = new Travel();
        cityPlaceRepository.save(new CityPlace());
        cityPlaceRepository.save(new CityPlace());
        cityPlaceRepository.save(new CityPlace());
        cityRepository.save(new City());
        cityRepository.save(new City());
        cityRepository.save(new City());
        travelRepository.save(travel);

        // when
        deletionManager.delete(travel);

        // then
        assertEquals(0, travelRepository.findAll().size());
        assertEquals(0, cityRepository.findAll().size());
        assertEquals(0, cityPlaceRepository.findAll().size());
    }
}