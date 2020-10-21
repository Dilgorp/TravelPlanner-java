package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.java.travelplanner.domain.City;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findByTravelUuidAndUserUuidOrderByTravelNumber(UUID travelUuid, UUID userUuid);

    City findByUuidAndTravelUuidAndUserUuid(UUID uuid, UUID travelUuid, UUID userUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city c WHERE c.travelUuid = ?1 AND c.userUuid = ?2")
    void deleteTravelCities(UUID travelUuid, UUID userUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city c WHERE c.travelUuid = ?1 AND c.userUuid = ?2 AND c.name = '' AND c.placesCount = 0")
    void clearEmptyTravelCities(UUID travelUuid, UUID userUuid);
}
