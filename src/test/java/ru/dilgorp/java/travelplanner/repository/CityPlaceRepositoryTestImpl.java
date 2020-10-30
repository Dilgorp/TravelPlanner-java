package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.travelplanner.domain.CityPlace;

import java.util.*;

public class CityPlaceRepositoryTestImpl implements CityPlaceRepository {

    private final Map<UUID, CityPlace> places = new LinkedHashMap<>();

    @Override
    public List<CityPlace> findPlacesByTravelUuidAndCityUuidAndUserUuid(UUID travelUuid, UUID cityUuid, UUID userUuid) {
        return List.copyOf(places.values());
    }

    @Override
    public List<CityPlace> findPlacesByTravelUuidAndUserUuid(UUID travelUuid, UUID userUuid) {
        return List.copyOf(places.values());
    }

    @Override
    public CityPlace findByUuidAndTravelUuidAndCityUuidAndUserUuid(UUID uuid, UUID travelUuid, UUID cityUuid, UUID userUuid) {
        return places.get(uuid);
    }

    @Override
    public void deleteTravelPlaces(UUID travelUuid, UUID userUuid) {
        places.clear();
    }

    @Override
    public void deleteCityPlaces(UUID travelUuid, UUID cityUuid, UUID userUuid) {
        places.clear();
    }

    @Override
    public void deletePlace(UUID uuid, UUID travelUuid, UUID cityUuid, UUID userUuid) {
        places.remove(uuid);
    }

    @Override
    public List<CityPlace> findAll() {
        return List.copyOf(places.values());
    }

    @Override
    public List<CityPlace> findAll(Sort sort) {
        return List.copyOf(places.values());
    }

    @Override
    public Page<CityPlace> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<CityPlace> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(CityPlace cityPlace) {

    }

    @Override
    public void deleteAll(Iterable<? extends CityPlace> iterable) {

    }

    @Override
    public void deleteAll() {
        places.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends CityPlace> S save(S s) {
        return (S) places.put(s.getUuid(), s);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends CityPlace> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> places.put(e.getUuid(), e));
        return (List<S>) List.copyOf(places.values());
    }

    @Override
    public Optional<CityPlace> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends CityPlace> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<CityPlace> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CityPlace getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends CityPlace> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CityPlace> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends CityPlace> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends CityPlace> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CityPlace> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CityPlace> boolean exists(Example<S> example) {
        return false;
    }
}