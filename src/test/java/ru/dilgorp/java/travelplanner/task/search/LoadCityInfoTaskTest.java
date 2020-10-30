package ru.dilgorp.java.travelplanner.task.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.config.TestConfig;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
class LoadCityInfoTaskTest {

    @Autowired
    private SearchTaskOptions searchTaskOptions;

    @Autowired
    private UserRequestRepository userRequestRepository;

    private LoadCityInfoTask loadCityInfoTask;

    @AfterEach
    void tearDown() {
        userRequestRepository.deleteAll();
    }

    @Test
    void constructorIsCorrect(){
        loadCityInfoTask = new LoadCityInfoTask(
                "City",
                null,
                searchTaskOptions
        );
        assertNotNull(loadCityInfoTask);
    }

    @Test
    void loadCityInfoWithoutPhotoWithoutRequestIsCorrect(){
        // given
        String cityName = "City";

        loadCityInfoTask = new LoadCityInfoTask(
                cityName,
                null,
                searchTaskOptions
        );
        UserRequest userRequest = new UserRequest();
        userRequest.setFormattedAddress("formattedAddress");
        userRequest.setName(cityName);

        // when
        loadCityInfoTask.run();
        UserRequest ur = userRequestRepository.getOne(userRequest.getUuid());

        // then
        assertNotNull(ur);
        assertEquals(userRequest.getFormattedAddress(), ur.getFormattedAddress());
        assertEquals(userRequest.getName(), ur.getName());
        assertNull(ur.getImagePath());
    }

    @Test
    void loadCityInfoWithoutPhotoWithRequestIsCorrect(){
        // given
        String cityName = "City";
        UserRequest userRequest = new UserRequest();
        userRequest.setUuid(UUID.randomUUID());
        userRequest.setFormattedAddress("formattedAddress");
        userRequest.setName(cityName);
        userRequest.setImagePath("123");

        loadCityInfoTask = new LoadCityInfoTask(
                cityName,
                userRequest,
                searchTaskOptions
        );


        // when
        loadCityInfoTask.run();
        UserRequest ur = userRequestRepository.getOne(userRequest.getUuid());

        // then
        assertNotNull(ur);
        assertEquals(userRequest.getFormattedAddress(), ur.getFormattedAddress());
        assertEquals(userRequest.getName(), ur.getName());
        assertEquals("123", ur.getImagePath());
    }

    @Test
    void loadCityInfoIsCorrect(){
        // given
        String cityName = "CityWithPhoto";
        UserRequest userRequest = new UserRequest();
        userRequest.setUuid(UUID.randomUUID());
        userRequest.setFormattedAddress("formattedAddress");
        userRequest.setName(cityName);
        userRequest.setImagePath("123");

        loadCityInfoTask = new LoadCityInfoTask(
                cityName,
                userRequest,
                searchTaskOptions
        );


        // when
        loadCityInfoTask.run();
        UserRequest ur = userRequestRepository.getOne(userRequest.getUuid());

        // then
        assertNotNull(ur);
        assertEquals(userRequest.getFormattedAddress(), ur.getFormattedAddress());
        assertEquals(userRequest.getName(), ur.getName());
        assertEquals(
                ur.getUuid().toString() + ur.getName() + "img/jpeg" + "D:\\test",
                ur.getImagePath()
        );
    }

}