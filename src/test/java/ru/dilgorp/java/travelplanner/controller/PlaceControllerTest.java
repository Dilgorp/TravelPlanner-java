package ru.dilgorp.java.travelplanner.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.config.TestConfig;
import ru.dilgorp.java.travelplanner.domain.CityPlace;
import ru.dilgorp.java.travelplanner.domain.DataGenerator;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.dilgorp.java.travelplanner.response.ResponseType.SUCCESS;

@SpringJUnitConfig({RepositoriesConfig.class, TestConfig.class})
class PlaceControllerTest {

    @Autowired
    private CityPlaceRepository cityPlaceRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private DataGenerator dataGenerator;

    private PlaceController placeController;

    @BeforeEach
    void setUp() {
        placeController = new PlaceController(
                cityPlaceRepository,
                fileService
        );
    }

    @AfterEach
    void tearDown() {
        cityPlaceRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect(){
        assertNotNull(placeController);
    }

    @Test
    void getPlacesIsCorrect(){
        // given
        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(3);
        cityPlaces.forEach(cityPlaceRepository::save);
        Response<List<CityPlace>> response = new Response<>(SUCCESS, "", cityPlaces);

        // when
        Response<List<CityPlace>> controllerResponse = placeController.getPlaces(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void addPlacesIdCorrect(){
        // given
        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(6);
        cityPlaces.forEach(cityPlaceRepository::save);
        List<Place> places = new ArrayList<>();
        for(int i = 0; i < 3; i ++){
            Place place = new Place();
            place.setName(cityPlaces.get(i).getName());
            place.setDescription(cityPlaces.get(i).getDescription());
            place.setImagePath(cityPlaces.get(i).getImagePath());
            places.add(place);
        }

        Response<List<CityPlace>> response = new Response<>(SUCCESS, "", null);

        // when
        Response<List<CityPlace>> controllerResponse = placeController.addPlaces(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                places
        );

        // then
        assertEquals(response, controllerResponse);
        assertEquals(1, cityPlaceRepository.findAll().size());
    }

    @Test
    void deletePlaceIsCorrect(){
        // given
        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(3);
        cityPlaces.forEach(cityPlaceRepository::save);

        List<CityPlace> placesToResponse = new ArrayList<>(cityPlaces);
        placesToResponse.remove(2);

        Response<List<CityPlace>> response = new Response<>(SUCCESS, "", placesToResponse);

        // when
        Response<List<CityPlace>> controllerResponse = placeController.deletePlace(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                cityPlaces.get(2).getUuid()
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void getCityPlaceImageIsCorrect(){
        // given
        List<CityPlace> cityPlaces = dataGenerator.generateCityPlaces(1);
        cityPlaces.forEach(cityPlaceRepository::save);

        // when
        byte[] response = placeController.getCityPlacePhoto(
                cityPlaces.get(0).getUuid(),
                cityPlaces.get(0).getUuid(),
                cityPlaces.get(0).getUuid(),
                cityPlaces.get(0).getUuid()
        );

        // then
        assertEquals(fileService.getBytes(""), response);
    }

    @Test
    void getTravelImageWithoutTravelIsCorrect(){
        // when
        byte[] response = placeController.getCityPlacePhoto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        // then
        assertNotNull(response);
        assertEquals(0, response.length);
        assertNotEquals(fileService.getBytes(""), response);
    }
}