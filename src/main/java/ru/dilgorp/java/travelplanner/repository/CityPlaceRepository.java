package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.java.travelplanner.domain.CityPlace;

import java.util.List;
import java.util.UUID;

public interface CityPlaceRepository extends JpaRepository<CityPlace, UUID> {
    List<CityPlace> findPlacesByTravelUuidAndCityUuid(UUID travelUuid, UUID cityUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city_place cp WHERE cp.travelUuid = ?1 AND cp.cityUuid = ?2")
    void deleteCityPlaces(UUID travelUuid, UUID cityUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city_place cp WHERE cp.uuid = ?1 AND cp.travelUuid = ?2 AND cp.cityUuid = ?3")
    void deletePlace(UUID uuid, UUID travelUuid, UUID cityUuid);
}
