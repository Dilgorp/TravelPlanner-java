package ru.dilgorp.java.travelplanner.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataGenerator {
    public List<CityPlace> generateCityPlaces(int size) {
        List<CityPlace> places = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CityPlace cityPlace = new CityPlace();
            cityPlace.setUuid(UUID.randomUUID());
            cityPlace.setName(Integer.toString(i));
            cityPlace.setDescription(Integer.toString(i));
            cityPlace.setImagePath("CityPlace" + i);
            cityPlace.setCityUuid(UUID.randomUUID());
            cityPlace.setTravelUuid(UUID.randomUUID());
            cityPlace.setUserUuid(UUID.randomUUID());
            places.add(cityPlace);
        }
        return places;
    }

    public List<City> generateCities(int size) {
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            City city = new City();
            city.setUuid(UUID.randomUUID());
            city.setName(Integer.toString(i));
            city.setDescription(Integer.toString(i));
            city.setImagePath("City" + i);
            city.setUserRequestUuid(UUID.randomUUID());
            city.setTravelUuid(UUID.randomUUID());
            city.setUserUuid(UUID.randomUUID());
            city.setTravelNumber(i);
            city.setPlacesCount(i);
            cities.add(city);
        }

        return cities;
    }

    public List<Travel> generateTravels(int size){
        List<Travel> travels = new ArrayList<>();

        for(int i = 0; i < size; i++){
            Travel travel = new Travel();
            travel.setUuid(UUID.randomUUID());
            travel.setName(Integer.toString(i));
            travel.setImagePath("Travel" + i);
            travel.setUserUuid(UUID.randomUUID());
            travels.add(travel);
        }

        return travels;
    }

    public Travel copy(Travel travel){
        Travel newTravel = new Travel();

        newTravel.setUuid(travel.getUuid());
        newTravel.setName(travel.getName());
        newTravel.setImagePath(travel.getImagePath());
        newTravel.setUserUuid(travel.getUserUuid());
        newTravel.setCitiesCount(travel.getCitiesCount());
        newTravel.setPlacesCount(travel.getPlacesCount());

        return newTravel;
    }

    public City copy(City city){
        City newCity = new City();

        newCity.setUuid(city.getUuid());
        newCity.setName(city.getName());
        newCity.setDescription(city.getDescription());
        newCity.setImagePath(city.getImagePath());
        newCity.setUserRequestUuid(city.getUserRequestUuid());
        newCity.setTravelUuid(city.getTravelUuid());
        newCity.setUserUuid(city.getUserUuid());
        newCity.setTravelNumber(city.getTravelNumber());

        return newCity;
    }

    public CityPlace copy(CityPlace cityPlace){
        CityPlace newCityPlace = new CityPlace();

        cityPlace.setUuid(cityPlace.getUuid());
        cityPlace.setName(cityPlace.getName());
        cityPlace.setDescription(cityPlace.getDescription());
        cityPlace.setImagePath(cityPlace.getImagePath());
        cityPlace.setCityUuid(cityPlace.getCityUuid());
        cityPlace.setTravelUuid(cityPlace.getTravelUuid());
        cityPlace.setUserUuid(cityPlace.getUserUuid());

        return newCityPlace;
    }
}
