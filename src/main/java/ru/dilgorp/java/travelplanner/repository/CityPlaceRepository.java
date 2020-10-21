package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.java.travelplanner.domain.CityPlace;

import java.util.List;
import java.util.UUID;

public interface CityPlaceRepository extends JpaRepository<CityPlace, UUID> {
    List<CityPlace> findPlacesByTravelUuidAndCityUuidAndUserUuid(UUID travelUuid, UUID cityUuid, UUID userUuid);

    List<CityPlace> findPlacesByTravelUuidAndUserUuid(UUID travelUuid, UUID userUuid);

    CityPlace findByUuidAndTravelUuidAndCityUuidAndUserUuid(UUID uuid, UUID travelUuid, UUID cityUuid, UUID userUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city_place cp WHERE cp.travelUuid = ?1 AND cp.userUuid = ?2")
    void deleteTravelPlaces(UUID travelUuid, UUID userUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city_place cp WHERE cp.travelUuid = ?1 AND cp.cityUuid = ?2 AND cp.userUuid = ?3")
    void deleteCityPlaces(UUID travelUuid, UUID cityUuid, UUID userUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city_place cp WHERE cp.uuid = ?1 AND cp.travelUuid = ?2 AND cp.cityUuid = ?3 AND cp.userUuid = ?4")
    void deletePlace(UUID uuid, UUID travelUuid, UUID cityUuid, UUID userUuid);
}
