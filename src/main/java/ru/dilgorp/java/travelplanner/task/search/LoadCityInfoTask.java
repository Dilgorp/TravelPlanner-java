package ru.dilgorp.java.travelplanner.task.search;

import com.google.maps.ImageResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import lombok.SneakyThrows;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;
import ru.dilgorp.java.travelplanner.repository.google.api.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

import java.io.IOException;
import java.util.Date;

public class LoadCityInfoTask implements Runnable {

    private static final int SEC_PER_DAY = 86400;

    private final String cityName;
    private final UserRequest userRequestFromDB;
    private final SearchTaskOptions searchTaskOptions;


    public LoadCityInfoTask(String cityName, UserRequest userRequestFromDB, SearchTaskOptions searchTaskOptions) {
        this.cityName = cityName;
        this.userRequestFromDB = userRequestFromDB;
        this.searchTaskOptions = searchTaskOptions;
    }

    @Override
    public void run() {
        loadCityInfo();
    }

    @SneakyThrows
    private void loadCityInfo() {

        PlaceDetails placeDetails = searchTaskOptions
                .getPlaceApiService()
                .getCityDetails(
                        cityName,
                        searchTaskOptions.getLanguage()
                );


        UserRequest request = userRequestFromDB == null ? new UserRequest() : userRequestFromDB;
        fillUserRequest(request, placeDetails, cityName);

        searchTaskOptions.getUserRequestRepository().save(request);
        if (placeDetails.photos != null && placeDetails.photos.length > 0) {
            loadCityImage(placeDetails, request.getText());
        }

    }

    private void loadCityImage(PlaceDetails placeDetails, String text) throws InterruptedException, ApiException, IOException {
        UserRequestRepository userRequestRepository = searchTaskOptions.getUserRequestRepository();
        UserRequest request = userRequestRepository.findByText(text);

        ImageResult imageResult;

        imageResult = searchTaskOptions.getPlaceApiService().getImageDetails(
                placeDetails.photos[0].photoReference,
                placeDetails.photos[0].width
        );

        String filePath = searchTaskOptions.getFileService().createPath(
                request.getUuid(),
                placeDetails.name,
                imageResult.contentType,
                searchTaskOptions.getPhotosFolder()
        );

        searchTaskOptions.getFileService().saveFile(imageResult.imageData, filePath);
        request.setImagePath(filePath);
        userRequestRepository.save(request);
    }

    private void fillUserRequest(UserRequest request, PlaceDetails details, String cityName) {
        request.setExpired(new Date(System.currentTimeMillis() + (SEC_PER_DAY * searchTaskOptions.getExpiredDays() * 1000)));
        request.setFormattedAddress(details.formattedAddress);
        request.setName(details.name);
        request.setText(cityName.toLowerCase());
    }
}
