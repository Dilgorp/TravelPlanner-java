package ru.dilgorp.java.travelplanner.controller;

import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlaceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.travelplanner.domain.UserRequest;
import ru.dilgorp.java.travelplanner.repository.UserRequestRepository;
import ru.dilgorp.java.travelplanner.response.CitySearchResponse;
import ru.dilgorp.java.travelplanner.response.ResponseType;

import java.io.*;
import java.util.Date;
import java.util.UUID;

@RestController
public class SearchController {

    public static final int SEC_PER_DAY = 86400;
    public static final String SEARCH_CITY_PATH = "/search/city/{cityname}";
    public static final String SEARCH_CITY_PHOTOS_PATH = "/search/photo/city/";
    public static final String SEARCH_CITY_PHOTO_PATH = SEARCH_CITY_PHOTOS_PATH + "{uuid}";

    @Value("${google.place-api.key}")
    private String googlePlaceApiKey;

    @Value("${google.place-api.language}")
    private String googlePlaceLanguage;

    @Value("${google.place-api.photos.folder}")
    private String googlePlacePhotosFolder;

    @Value("${google.place-api.expired-days}")
    private int googlePlaceExpiredDays;

    private final UserRequestRepository userRequestRepository;

    public SearchController(UserRequestRepository userRequestRepository) {
        this.userRequestRepository = userRequestRepository;
    }

    @RequestMapping(value = SEARCH_CITY_PATH, method = RequestMethod.GET)
    public CitySearchResponse getCityInfo(@PathVariable("cityname") String cityName) {
        String textSearch = cityName.toLowerCase();
        UserRequest userRequestFromDB = userRequestRepository.findByText(textSearch);
        if (userRequestFromDB == null || userRequestFromDB.getExpired().compareTo(new Date()) <= 0) {
            loadCityInfo(cityName);
        }

        userRequestFromDB = userRequestRepository.findByText(textSearch);
        CitySearchResponse response;
        if (userRequestFromDB == null) {
            response = new CitySearchResponse(ResponseType.ERROR, "Город не найден", null);
        } else {
            response = new CitySearchResponse(ResponseType.SUCCESS, "", userRequestFromDB);
        }

        return response;
    }

    @RequestMapping(value = SEARCH_CITY_PHOTO_PATH, method = RequestMethod.GET)
    public byte[] getCityPhoto(@PathVariable("uuid") UUID uuid) throws IOException {
        UserRequest requestFromDB = userRequestRepository.getOne(uuid);

        InputStream inputStream = new FileInputStream(requestFromDB.getImagePath());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(inputStream.readAllBytes());

        return outputStream.toByteArray();
    }

    private void loadCityInfo(String cityName) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(googlePlaceApiKey).build();
        try {
            FindPlaceFromText response =
                    PlacesApi.findPlaceFromText(
                            context,
                            cityName,
                            FindPlaceFromTextRequest.InputType.TEXT_QUERY
                    ).await();

            if (response.candidates.length < 1) {
                return;
            }

            PlaceDetails placeDetails =
                    PlacesApi.placeDetails(context, response.candidates[0].placeId)
                            .language(googlePlaceLanguage)
                            .await();

            UserRequest request = new UserRequest();
            fillUserRequest(request, placeDetails, cityName);

            if (placeDetails.photos.length < 1) {
                userRequestRepository.save(request);
                return;
            }

            ImageResult imageResult =
                    PlacesApi.photo(context, placeDetails.photos[0].photoReference)
                            .maxWidth(placeDetails.photos[0].width).
                            await();

            String filePath = googlePlacePhotosFolder + placeDetails.name + "." + imageResult.contentType.split("/")[1];
            saveImage(imageResult, filePath);
            request.setImagePath(filePath);
            userRequestRepository.save(request);
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void fillUserRequest(UserRequest request, PlaceDetails details, String cityName) {
        request.setExpired(new Date(System.currentTimeMillis() + (SEC_PER_DAY * googlePlaceExpiredDays * 1000)));
        request.setFormattedAddress(details.formattedAddress);
        request.setName(details.name);
        request.setText(cityName.toLowerCase());
    }

    private void saveImage(ImageResult imageResult, String filePath) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(imageResult.imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
