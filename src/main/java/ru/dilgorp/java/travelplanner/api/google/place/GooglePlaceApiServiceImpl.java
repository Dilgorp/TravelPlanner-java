package ru.dilgorp.java.travelplanner.api.google.place;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;

import java.io.IOException;

public class GooglePlaceApiServiceImpl implements GooglePlaceApiService {
    private final GeoApiContext context;

    public GooglePlaceApiServiceImpl(GeoApiContext context) {
        this.context = context;
    }

    @Override
    public PlaceDetails getCityDetails(String cityName, String language) throws InterruptedException, ApiException, IOException {
        FindPlaceFromText response =
                PlacesApi.findPlaceFromText(
                        context,
                        cityName,
                        FindPlaceFromTextRequest.InputType.TEXT_QUERY
                ).await();

        if (response.candidates.length < 1) {
            return null;
        }

        return PlacesApi.placeDetails(context, response.candidates[0].placeId)
                .language(language)
                .await();
    }

    @Override
    public ImageResult getImageDetails(String photoReference, int maxWidth) throws InterruptedException, ApiException, IOException {
        return PlacesApi.photo(context, photoReference).maxWidth(maxWidth).await();
    }

    @Override
    public ImageResult getImageByPlaceId(String placeId, String language) throws InterruptedException, ApiException, IOException {
        PlaceDetails placeDetails = PlacesApi.placeDetails(context, placeId)
                .language(language)
                .await();

        if (placeDetails.photos.length < 1) {
            return null;
        }

        return PlacesApi.photo(context, placeDetails.photos[0].photoReference)
                .maxWidth(placeDetails.photos[0].width)
                .await();
    }

    @Override
    public PlacesSearchResponse getPlaces(String query, String language, String pageToken) throws InterruptedException, ApiException, IOException {
        TextSearchRequest textSearchRequest = PlacesApi.textSearchQuery(context, query);
        if (pageToken != null && !pageToken.isEmpty()) {
            textSearchRequest.pageToken(pageToken);
        }

        return textSearchRequest.language(language).await();
    }
}
