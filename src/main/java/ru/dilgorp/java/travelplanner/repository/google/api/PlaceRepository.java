package ru.dilgorp.java.travelplanner.repository.google.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID>{
    List<Place> findByUserRequestUuid(UUID userRequestUuid);

    @Query("SELECT COUNT(p.uuid) FROM place p WHERE p.userRequestUuid = ?1")
    int findCountByUserRequestUuid(UUID userRequestUUID);

    List<Place> findByUserRequestUuidAndCurrentPageToken(UUID requestUuid, String pageToken);
}
