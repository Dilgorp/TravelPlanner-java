package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.java.travelplanner.domain.City;

import java.util.List;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findByTravelUuid(UUID travelUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM city c WHERE c.travelUuid = ?1")
    void deleteTravelCities(UUID travelUuid);
}
