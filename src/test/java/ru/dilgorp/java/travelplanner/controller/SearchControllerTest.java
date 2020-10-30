package ru.dilgorp.java.travelplanner.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.RepositoriesConfig;
import ru.dilgorp.java.travelplanner.config.TestConfig;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.DataGenerator;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.response.Response;
import ru.dilgorp.java.travelplanner.response.ResponseType;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig({RepositoriesConfig.class, TestConfig.class})
class SearchControllerTest {

    private static final String NULL = "null";
    private static final String SEARCH_SUFFIX_PLACE_BY_CITY_NAME = "+city+point+of+interest";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SearchTaskOptions searchTaskOptions;

    @Autowired
    private SyncTaskExecutor syncTaskExecutor;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private UserRequestRepository userRequestRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private FileService fileService;

    private SearchController searchController;

    @BeforeEach
    void setUp() {
        searchController = new SearchController(
                searchTaskOptions,
                syncTaskExecutor,
                threadPoolTaskExecutor,
                cityRepository
        );
    }

    @AfterEach
    void tearDown() {
        cityRepository.deleteAll();
        placeRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect() {
        assertNotNull(searchController);
    }

    @Test
    void getCityInfoNotFound() {
        // given
        Response<City> response = new Response<>(ResponseType.ERROR, "Город не найден", null);

        // when
        Response<City> controllerResponse = searchController.getCityInfo(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "null"
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void getCityInfoIsCorrect() {
        // Given
        City city = new City();
        city.setUuid(UUID.randomUUID());
        city.setTravelUuid(UUID.randomUUID());
        city.setUserUuid(UUID.randomUUID());

        cityRepository.save(dataGenerator.copy(city));

        city.setName("City");
        city.setDescription("formattedAddress");
        Response<City> response = new Response<>(ResponseType.SUCCESS, "", city);

        // when
        Response<City> controllerResponse = searchController.getCityInfo(
                city.getUserUuid(),
                city.getTravelUuid(),
                city.getUuid(),
                city.getName()
        );

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void getCityPlacesNotFound() {
        // given
        UserRequest userRequest = new UserRequest();
        userRequest.setName(NULL);
        userRequest.setUuid(UUID.randomUUID());
        Response<List<Place>> response = new Response<>(
                ResponseType.ERROR,
                "Интересные места не найдены",
                Collections.emptyList()
        );

        // when
        Response<List<Place>> controllerResponse = searchController.getCityPlaces(userRequest.getUuid());

        // then
        assertEquals(response, controllerResponse);
    }

    @Test
    void getCityPlacesIsCorrect() {
        // given
        UserRequest userRequest = new UserRequest();
        userRequest.setName("City");
        userRequest.setUuid(UUID.randomUUID());
        userRequestRepository.save(userRequest);

        // when
        Response<List<Place>> controllerResponse = searchController.getCityPlaces(userRequest.getUuid());

        // then
        assertEquals(ResponseType.SUCCESS, controllerResponse.getType());
        assertEquals(2, placeRepository.findAll().size());
    }

    @Test
    void getPlacePhotoIsCorrect(){
        // given
        Place place = new Place();
        place.setUuid(UUID.randomUUID());
        placeRepository.save(place);

        // when
        byte[] response = searchController.getPlacePhoto(
                place.getUuid()
        );

        // then
        assertEquals(fileService.getBytes(""), response);
    }
}