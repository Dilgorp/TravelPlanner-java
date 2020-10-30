package ru.dilgorp.java.travelplanner.controller;

import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.config.TestConfig;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.DataGenerator;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManagerImpl;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringJUnitConfig({RepositoriesConfig.class, TestConfig.class})
class TravelControllerTest {
    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityPlaceRepository cityPlaceRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private DeletionManagerImpl deletionManager;

    private TravelController travelController;

    @BeforeEach
    void setUp() {
        travelController = new TravelController(
                deletionManager,
                travelRepository,
                cityRepository,
                cityPlaceRepository,
                fileService
        );
    }

    @AfterEach
    void tearDown() {
        travelRepository.deleteAll();
        cityRepository.deleteAll();
        cityPlaceRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect() {
        assertNotNull(travelController);
    }

    @Test
    void addTravelIsCorrect() {
        // given
        UUID userUuid = UUID.randomUUID();
        Travel travel = new Travel("", 0, 0, "", userUuid);
        Response<Travel> response = new Response<>(ResponseType.SUCCESS, "", travel);

        // when
        Response<Travel> travelResponse = travelController.addTravel(userUuid);

        // then
        assertEquals(response.getType(), travelResponse.getType());
        assertEquals(1, travelRepository.findAll().size());
    }

    @Test
    void getTravelIsCorrect() {
        // given
        UUID travelUuid = UUID.randomUUID();
        Travel travel = new Travel("", 0, 0, "", travelUuid);
        travel.setUuid(travelUuid);
        travelRepository.save(travel);
        Response<Travel> response = new Response<>(
                ResponseType.SUCCESS,
                "",
                travelRepository.findByUuidAndUserUuid(travelUuid, travelUuid)
        );

        // when
        Response<Travel> travelResponse = travelController.getTravel(travelUuid, travelUuid);

        // then
        assertEquals(response, travelResponse);
    }

    @Test
    void deleteTravelIsCorrect() {
        // given
        UUID travelUuid = UUID.randomUUID();
        Travel travel = new Travel("", 0, 0, "", travelUuid);
        travel.setUuid(travelUuid);
        travelRepository.save(travel);
        Response<List<Travel>> response = new Response<>(
                ResponseType.SUCCESS,
                "",
                Collections.emptyList()
        );
        dataGenerator.generateCities(3).forEach(cityRepository::save);
        dataGenerator.generateCityPlaces(3).forEach(cityPlaceRepository::save);

        // when
        Response<List<Travel>> travelResponse = travelController.deleteTravel(travelUuid, travelUuid);

        // then
        assertEquals(response, travelResponse);
        assertEquals(0, cityRepository.findAll().size());
        assertEquals(0, cityPlaceRepository.findAll().size());
    }

    @Test
    void refreshTravel0Cities() {
        // given
        UUID travelUuid = UUID.randomUUID();
        Travel travel = new Travel("", 0, 0, "", travelUuid);

        travel.setUuid(travelUuid);
        travelRepository.save(dataGenerator.copy(travel));

        // when
        Travel travelResponse = travelController.refreshTravel(travelUuid, travelUuid).getData();

        // then
        assertEquals(travel, travelResponse);
    }

    @Test
    void refreshTravel1Cities() {
        // given
        List<City> cities = dataGenerator.generateCities(1);
        cities.forEach(cityRepository::save);

        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(3);
        cityPlaces.forEach(cityPlaceRepository::save);

        UUID travelUuid = UUID.randomUUID();
        Travel travel = new Travel(
                cities.get(0).getName(),
                1,
                3,
                cities.get(0).getImagePath(),
                travelUuid
        );

        travel.setUuid(travelUuid);
        travelRepository.save(dataGenerator.copy(travel));

        // when
        Travel travelResponse = travelController.refreshTravel(travelUuid, travelUuid).getData();

        // then
        assertEquals(travel, travelResponse);
    }

    @Test
    void refreshTravel2Cities() {
        // given
        List<City> cities = dataGenerator.generateCities(2);
        cities.forEach(cityRepository::save);

        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(3);
        cityPlaces.forEach(cityPlaceRepository::save);

        UUID travelUuid = UUID.randomUUID();
        Travel travel = new Travel(
                cities.get(0).getName() + " - " + cities.get(1).getName(),
                2,
                3,
                cities.get(0).getImagePath(),
                travelUuid
        );

        travel.setUuid(travelUuid);
        travelRepository.save(dataGenerator.copy(travel));

        // when
        Travel travelResponse = travelController.refreshTravel(travelUuid, travelUuid).getData();

        // then
        assertEquals(travel, travelResponse);
    }

    @Test
    void refreshTravel3Cities() {
        // given
        List<City> cities = dataGenerator.generateCities(3);
        cities.forEach(cityRepository::save);

        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(3);
        cityPlaces.forEach(cityPlaceRepository::save);

        UUID travelUuid = UUID.randomUUID();
        Travel travel = new Travel(
                cities.get(0).getName() + " - ... - " + cities.get(2).getName(),
                3,
                3,
                cities.get(0).getImagePath(),
                travelUuid
        );

        travel.setUuid(travelUuid);
        travelRepository.save(dataGenerator.copy(travel));

        // when
        Travel travelResponse = travelController.refreshTravel(travelUuid, travelUuid).getData();

        // then
        assertEquals(travel, travelResponse);
    }

    @Test
    void allTravelsIsCorrect() {
        // given
        dataGenerator.generateTravels(3).forEach(travelRepository::save);
        Response<List<Travel>> response = new Response<>(
                ResponseType.SUCCESS,
                "",
                Collections.emptyList()
        );

        // when
        Response<List<Travel>> travelResponse = travelController.allTravels(UUID.randomUUID());

        // then
        assertEquals(response, travelResponse);
    }

    @Test
    void getTravelImageIsCorrect(){
        // given
        List<Travel> travels = dataGenerator.generateTravels(1);
        travels.forEach(travelRepository::save);

        // when
        byte[] response = travelController.getTravelImage(
                travels.get(0).getUuid(), travels.get(0).getUuid()
        );

        // then
        assertEquals(fileService.getBytes(""), response);
    }

    @Test
    void getTravelImageWithoutTravelIsCorrect(){
        // when
        byte[] response = travelController.getTravelImage(
                UUID.randomUUID(), UUID.randomUUID()
        );

        // then
        assertNotNull(response);
        assertEquals(0, response.length);
        assertNotEquals(fileService.getBytes(""), response);
    }
}