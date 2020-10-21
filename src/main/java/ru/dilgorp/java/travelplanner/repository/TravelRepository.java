package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.java.travelplanner.domain.Travel;

import java.util.List;
import java.util.UUID;

public interface TravelRepository extends JpaRepository<Travel, UUID> {
    Travel findByUuidAndUserUuid(UUID uuid, UUID userUuid);
    List<Travel> findByUserUuid(UUID userUuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM Travel t WHERE t.citiesCount = 0 AND t.userUuid = ?1")
    void clearEmptyTravels(UUID userUuid);
}
