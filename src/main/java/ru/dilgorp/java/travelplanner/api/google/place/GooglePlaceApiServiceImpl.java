package ru.dilgorp.java.travelplanner.api.google.place;

import com.google.maps.*;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class GooglePlaceApiServiceImpl implements GooglePlaceApiService {
    private final GeoApiContext context;

    @Override
    @SneakyThrows
    public PlaceDetails getCityDetails(String cityName, String language){
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
    @SneakyThrows
    public ImageResult getImageDetails(String photoReference, int maxWidth){
        return PlacesApi.photo(context, photoReference).maxWidth(maxWidth).await();
    }

    @Override
    @SneakyThrows
    public ImageResult getImageByPlaceId(String placeId, String language){
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
    @SneakyThrows
    public PlacesSearchResponse getPlaces(String query, String language, String pageToken){
        TextSearchRequest textSearchRequest = PlacesApi.textSearchQuery(context, query);
        if (pageToken != null && !pageToken.isEmpty()) {
            textSearchRequest.pageToken(pageToken);
        }

        return textSearchRequest.language(language).await();
    }
}
