package ru.dilgorp.java.travelplanner.repository.google.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID>{
    List<Place> findByUserRequestUUIDAndCurrentPageToken(UUID userRequestUUID, String currentPageToken);

    @Query("SELECT COUNT(p.uuid) FROM place p WHERE p.userRequestUUID = ?1")
    int findCountByUserRequestUUID(UUID userRequestUUID);
}
