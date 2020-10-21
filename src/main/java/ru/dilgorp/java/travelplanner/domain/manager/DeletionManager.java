package ru.dilgorp.java.travelplanner.domain.manager;

import ru.dilgorp.java.travelplanner.domain.City;
import ru.dilgorp.java.travelplanner.domain.Travel;

import java.util.UUID;

public interface DeletionManager {
    void delete(Travel travel);
    void delete(City city);
    void clearEmptyTravels(UUID userUuid);
}
