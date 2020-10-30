package ru.dilgorp.java.travelplanner.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.config.TestConfig;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.DataGenerator;
import ru.dilgorp.java.travelplanner.domain.manager.DeletionManagerImpl;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.dilgorp.java.travelplanner.response.ResponseType.ERROR;
import static ru.dilgorp.java.travelplanner.response.ResponseType.SUCCESS;

@SpringJUnitConfig({RepositoriesConfig.class, TestConfig.class})
class CityControllerTest {

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

    private CityController cityController;

    @BeforeEach
    void setUp() {
        cityController = new CityController(
                cityRepository,
                cityPlaceRepository,
                deletionManager,
                fileService
        );
    }

    @AfterEach
    void tearDown() {
        cityRepository.deleteAll();
        cityPlaceRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect(){
        assertNotNull(cityController);
    }

    @Test
    void getCitiesIsCorrect(){
        // given
        List<City> cities = dataGenerator.generateCities(3);
        cities.forEach(cityRepository::save);
        cities.remove(0);
        Response<List<City>> response = new Response<>(SUCCESS, "", cities);

        // when
        Response<List<City>> controllerResponse = cityController.getCities(UUID.randomUUID(), UUID.randomUUID());

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void addCityRepositoryEmptyIsCorrect(){
        // given
        UUID travelUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        City city = new City(
                "", 0, "", "",
                travelUuid, userUuid, 0
        );

        Response<City> response = new Response<>(SUCCESS, "", city);

        // when
        Response<City> controllerResponse = cityController.addCity(userUuid, travelUuid);

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void addCityRepositoryNotEmptyIsCorrect(){
        // given
        dataGenerator.generateCities(3).forEach(cityRepository::save);
        UUID travelUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        City city = new City(
                "", 0, "", "",
                travelUuid, userUuid, 3
        );

        Response<City> response = new Response<>(SUCCESS, "", city);

        // when
        Response<City> controllerResponse = cityController.addCity(userUuid, travelUuid);

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void getCityIsCorrect(){
        // given
        List<City> cities = dataGenerator.generateCities(3);
        cities.forEach(cityRepository::save);
        Response<City> response = new Response<>(SUCCESS, "", cities.get(2));

        // when
        Response<City> controllerResponse = cityController.getCity(
                UUID.randomUUID(), UUID.randomUUID(),
                cities.get(2).getUuid()
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void refreshCityIsCorrect(){
        // given
        dataGenerator.generateCityPlaces(3).forEach(cityPlaceRepository::save);
        List<City> cities = dataGenerator.generateCities(3);
        cities.forEach(cityRepository::save);
        City city =  dataGenerator.copy(cities.get(2));
        city.setPlacesCount(3);
        Response<City> response = new Response<>(SUCCESS, "", city);

        // when
        Response<City> controllerResponse = cityController.refreshCity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                city.getUuid()
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void deleteCityIsCorrect(){
        // given
        List<City> cities = dataGenerator.generateCities(3);
        cities.forEach(cityRepository::save);
        List<City> responseCities = new ArrayList<>(cities);
        responseCities.remove(2);
        Response<List<City>> response = new Response<>(SUCCESS, "", responseCities);

        // when
        Response<List<City>> controllerResponse = cityController.deleteCity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                cities.get(2).getUuid()
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void getTravelImageIsCorrect(){
        // given
        List<City> cities = dataGenerator.generateCities(1);
        cities.forEach(cityRepository::save);

        // when
        byte[] response = cityController.getCityPhoto(
                cities.get(0).getUuid(),
                cities.get(0).getUuid(),
                cities.get(0).getUuid()
        );

        // then
        assertEquals(fileService.getBytes(""), response);
    }

    @Test
    void getTravelImageWithoutTravelIsCorrect(){
        // when
        byte[] response = cityController.getCityPhoto(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
        );

        // then
        assertNotNull(response);
        assertEquals(0, response.length);
        assertNotEquals(fileService.getBytes(""), response);
    }

    @Test
    void setCityNumberRepositoryEmptyIsCorrect(){
        // given
        Response<City> response = new Response<>(ERROR, "Город не найден", null);

        // when
        Response<City> controllerResponse = cityController.setCityNumber(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                2
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void setCityNumberRepositoryNotEmptyIsCorrect(){
        // given
        List<City> cities = dataGenerator.generateCities(3);
        cities.forEach(cityRepository::save);
        City city = dataGenerator.copy(cities.get(0));
        city.setTravelNumber(120);
        Response<City> response = new Response<>(SUCCESS, "", city);

        // when
        Response<City> controllerResponse = cityController.setCityNumber(
                UUID.randomUUID(),
                UUID.randomUUID(),
                cities.get(0).getUuid(),
                120
        );

        // then
        assertEquals(response, controllerResponse);
    }
}