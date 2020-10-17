package ru.dilgorp.java.travelplanner.task.search;

import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.ImageResult;
import com.google.maps.PlacesApi;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlaceDetails;
import ru.dilgorp.java.travelplanner.domain.UserRequest;
import ru.dilgorp.java.travelplanner.repository.UserRequestRepository;
import ru.dilgorp.java.travelplanner.task.search.options.SearchTaskOptions;

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

    private void loadCityInfo() {
        try {
            FindPlaceFromText response =
                    PlacesApi.findPlaceFromText(
                            searchTaskOptions.getContext(),
                            cityName,
                            FindPlaceFromTextRequest.InputType.TEXT_QUERY
                    ).await();

            if (response.candidates.length < 1) {
                return;
            }

            PlaceDetails placeDetails =
                    PlacesApi.placeDetails(searchTaskOptions.getContext(), response.candidates[0].placeId)
                            .language(searchTaskOptions.getLanguage())
                            .await();


            UserRequest request = userRequestFromDB == null ? new UserRequest() : userRequestFromDB;
            fillUserRequest(request, placeDetails, cityName);

            searchTaskOptions.getUserRequestRepository().save(request);
            if (placeDetails.photos.length > 0) {
                loadCityImage(placeDetails, request.getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCityImage(PlaceDetails placeDetails, String text) throws Exception {
        UserRequestRepository userRequestRepository = searchTaskOptions.getUserRequestRepository();
        UserRequest request = userRequestRepository.findByText(text);

        ImageResult imageResult =
                PlacesApi.photo(searchTaskOptions.getContext(), placeDetails.photos[0].photoReference)
                        .maxWidth(placeDetails.photos[0].width).
                        await();

        String filePath = LoadTasksUtils.createImagePath(
                request.getUuid(),
                placeDetails.name,
                imageResult,
                searchTaskOptions.getPhotosFolder()
        );

        LoadTasksUtils.saveImage(imageResult, filePath);
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
