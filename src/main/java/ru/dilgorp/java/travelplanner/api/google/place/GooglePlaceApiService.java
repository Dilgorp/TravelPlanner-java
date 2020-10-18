package ru.dilgorp.java.travelplanner.api.google.place;

import com.google.maps.ImageResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;

import java.io.IOException;

public interface GooglePlaceApiService {
    PlaceDetails getCityDetails(String cityName, String language) throws InterruptedException, ApiException, IOException;

    ImageResult getImageDetails(String photoReference, int maxWidth) throws InterruptedException, ApiException, IOException;

    ImageResult getImageByPlaceId(String placeId, String language) throws InterruptedException, ApiException, IOException;

    PlacesSearchResponse getPlaces(String query, String language, String pageToken) throws InterruptedException, ApiException, IOException;
}
