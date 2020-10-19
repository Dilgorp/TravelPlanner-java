package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.java.travelplanner.domain.Travel;

import java.util.UUID;

public interface TravelRepository extends JpaRepository<Travel, UUID> {
}
