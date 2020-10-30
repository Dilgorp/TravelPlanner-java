package ru.dilgorp.java.travelplanner.task.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.TestConfig;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
class LoadPlacesTaskTest {

    private final static String MORE_THEN_OPTIONS = "83b5b123-9cac-41f6-b45c-456a84f14d89";
    private final static String NO_USER_REQUEST = "83b5b123-9cac-41f6-b45c-456a84f14d90";

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private SearchTaskOptions searchTaskOptions;

    @Autowired
    private UserRequestRepository userRequestRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        placeRepository.deleteAll();
        userRequestRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect() {
        LoadPlacesTask task = new LoadPlacesTask(UUID.randomUUID(), "", searchTaskOptions);
        assertNotNull(task);
    }

    @Test
    void loadPlacesCountMoreThenOption() {
        // given
        LoadPlacesTask task = new LoadPlacesTask(
                UUID.fromString(MORE_THEN_OPTIONS),
                "",
                searchTaskOptions
        );

        // when
        task.run();

        // then
        assertEquals(0, placeRepository.findAll().size());
    }

    @Test
    void loadPlacesNoUserRequest() {
        // given
        LoadPlacesTask task = new LoadPlacesTask(
                UUID.fromString(NO_USER_REQUEST),
                "",
                searchTaskOptions
        );

        // when
        task.run();

        // then
        assertEquals(0, placeRepository.findAll().size());
    }

    @Test
    void loadPlacesIsCorrect() {
        // given
        UserRequest userRequest = new UserRequest();
        userRequest.setUuid(UUID.randomUUID());
        userRequest.setName("City");
        userRequestRepository.save(userRequest);

        LoadPlacesTask task = new LoadPlacesTask(
                userRequest.getUuid(),
                "",
                searchTaskOptions
        );

        // when
        task.run();

        // then
        List<Place> places = placeRepository.findAll();
        assertEquals(2, places.size());
        assertNotNull(places.get(0).getImagePath());
        assertNull(places.get(1).getImagePath());
    }
}