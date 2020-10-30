package ru.dilgorp.java.travelplanner.config;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.travelplanner.file.FileService;
import ru.dilgorp.java.travelplanner.repository.google.api.PlaceRepository;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Набор тестов для класса {@link TaskExecutorsConfig}
 */
@SpringJUnitConfig(TestConfig.class)
class TaskExecutorsConfigTest {

    @Autowired
    private UserRequestRepository userRequestRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    @Qualifier("googlePlaceApiKey")
    private String googlePlaceApiKey;

    @Autowired
    @Qualifier("googlePlaceLanguage")
    private String googlePlaceLanguage;

    @Autowired
    @Qualifier("photosFolder")
    private String photosFolder;

    @Autowired
    @Qualifier("expiredDays")
    private int expiredDays;

    @Autowired
    @Qualifier("placesMaxCount")
    private int placesMaxCount;

    private TaskExecutorsConfig taskExecutorsConfig;

    @BeforeEach
    void beforeTests(){
        taskExecutorsConfig = new TaskExecutorsConfig(
                userRequestRepository,
                placeRepository,
                fileService
        );
    }


    @Test
    void constructorIsCorrect() {
        // then
        assertNotNull(taskExecutorsConfig);
    }

    @Test
    void googlePlaceApiServiceIsCorrect() {
        // then
        assertNotNull(taskExecutorsConfig.googlePlaceApiService());
    }

    @SneakyThrows
    @Test
    void searchTaskOptionsCorrect() {
        // given
        Class<?> c = taskExecutorsConfig.getClass();

        Field googlePlaceApiKeyField = c.getDeclaredField("googlePlaceApiKey");
        googlePlaceApiKeyField.setAccessible(true);
        googlePlaceApiKeyField.set(taskExecutorsConfig, googlePlaceApiKey);

        Field googlePlaceLanguageField = c.getDeclaredField("googlePlaceLanguage");
        googlePlaceLanguageField.setAccessible(true);
        googlePlaceLanguageField.set(taskExecutorsConfig, googlePlaceLanguage);

        Field photosFolderField = c.getDeclaredField("photosFolder");
        photosFolderField.setAccessible(true);
        photosFolderField.set(taskExecutorsConfig, photosFolder);

        Field expiredDaysField = c.getDeclaredField("expiredDays");
        expiredDaysField.setAccessible(true);
        expiredDaysField.set(taskExecutorsConfig, expiredDays);

        Field placesMaxCountField = c.getDeclaredField("placesMaxCount");
        placesMaxCountField.setAccessible(true);
        placesMaxCountField.set(taskExecutorsConfig, placesMaxCount);

        // when
        SearchTaskOptions searchTaskOptions = taskExecutorsConfig.searchTaskOptions();

        // then
        assertNotNull(searchTaskOptions);
    }

    @Test
    void syncTaskExecutorIsCorrect() {
        // then
        assertNotNull(taskExecutorsConfig.syncTaskExecutor());
    }

    @Test
    void threadPoolTaskExecutorIsCorrect() {
         // then
        assertNotNull(taskExecutorsConfig.threadPoolTaskExecutor());
    }
}