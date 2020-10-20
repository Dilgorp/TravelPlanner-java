package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.java.travelplanner.domain.Travel;

import java.util.List;
import java.util.UUID;

public interface TravelRepository extends JpaRepository<Travel, UUID> {
    Travel findByUuidAndUserUuid(UUID uuid, UUID userUuid);
    List<Travel> findByUserUuid(UUID userUuid);
}
