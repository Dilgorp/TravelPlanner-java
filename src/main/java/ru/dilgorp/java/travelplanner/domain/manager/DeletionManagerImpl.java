package ru.dilgorp.java.travelplanner.domain.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.Travel;
import ru.dilgorp.java.travelplanner.repository.CityPlaceRepository;
import ru.dilgorp.java.travelplanner.repository.CityRepository;
import ru.dilgorp.java.travelplanner.repository.TravelRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletionManagerImpl implements DeletionManager {
    private final TravelRepository travelRepository;
    private final CityRepository cityRepository;
    private final CityPlaceRepository cityPlaceRepository;

    @Override
    @Modifying
    @Transactional
    public void delete(Travel travel) {
        cityPlaceRepository.deleteTravelPlaces(travel.getUuid(), travel.getUserUuid());
        cityRepository.deleteTravelCities(travel.getUuid(), travel.getUserUuid());
        travelRepository.delete(travel);
    }

    @Override
    @Modifying
    @Transactional
    public void delete(City city) {
        cityPlaceRepository.deleteCityPlaces(city.getTravelUuid(), city.getUuid(), city.getUserUuid());
        cityRepository.delete(city);
    }

    @Override
    @Modifying
    @Transactional
    public void clearEmptyTravels(UUID userUuid) {
        travelRepository.clearEmptyTravels(userUuid);
    }
}
