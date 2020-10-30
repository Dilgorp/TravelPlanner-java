package ru.dilgorp.java.travelplanner.service;

import com.google.maps.ImageResult;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import ru.dilgorp.java.travelplanner.api.google.place.GooglePlaceApiService;

public class GooglePlaceApiServiceTestImpl implements GooglePlaceApiService {

    private static final String NULL = "null";
    private static final String SEARCH_SUFFIX_PLACE_BY_CITY_NAME = "+city+point+of+interest";

    @Override
    public PlaceDetails getCityDetails(String cityName, String language) {
        if(cityName.equals(NULL)){
            return null;
        }

        PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.formattedAddress = "formattedAddress";
        placeDetails.name = cityName;
        if (!cityName.equals("City")) {
            Photo photo = new Photo();
            photo.photoReference = "photoReference";
            photo.width = 0;
            placeDetails.photos = new Photo[]{photo};
        }
        return placeDetails;
    }

    @Override
    public ImageResult getImageDetails(String photoReference, int maxWidth) {
        return new ImageResult("img/jpeg", new byte[0]);
    }

    @Override
    public ImageResult getImageByPlaceId(String placeId, String language) {
        if (placeId.equals(NULL)) {
            return null;
        }
        return new ImageResult("img/jpeg", new byte[0]);
    }

    @Override
    public PlacesSearchResponse getPlaces(String query, String language, String pageToken) {
        PlacesSearchResponse placesSearchResponse = new PlacesSearchResponse();
        if(query.equals(NULL + SEARCH_SUFFIX_PLACE_BY_CITY_NAME)){
            placesSearchResponse.results = new PlacesSearchResult[0];
            return placesSearchResponse;
        }

        if (pageToken.isEmpty()) {
            placesSearchResponse.nextPageToken = "1";
            placesSearchResponse.results = new PlacesSearchResult[3];

            placesSearchResponse.results[0] = new PlacesSearchResult();

            placesSearchResponse.results[1] = new PlacesSearchResult();
            placesSearchResponse.results[1].photos = new Photo[0];

            placesSearchResponse.results[2] = new PlacesSearchResult();
            Photo photo = new Photo();
            photo.width = 10;
            photo.photoReference = "photoReference";
            placesSearchResponse.results[2].photos = new Photo[]{photo};
            placesSearchResponse.results[2].formattedAddress = "formattedAddress";
            placesSearchResponse.results[2].name = "name";
            placesSearchResponse.results[2].placeId = "placeId";
        } else {
            placesSearchResponse.nextPageToken = "";
            placesSearchResponse.results = new PlacesSearchResult[1];

            placesSearchResponse.results[0] = new PlacesSearchResult();
            Photo photo = new Photo();
            photo.width = 10;
            photo.photoReference = "null";
            placesSearchResponse.results[0].photos = new Photo[]{photo};
            placesSearchResponse.results[0].formattedAddress = "formattedAddress";
            placesSearchResponse.results[0].name = "name";
            placesSearchResponse.results[0].placeId = NULL;
        }

        return placesSearchResponse;
    }
}
