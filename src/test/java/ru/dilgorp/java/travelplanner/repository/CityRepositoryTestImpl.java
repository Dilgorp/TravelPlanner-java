package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.travelplanner.domain.City;

import java.util.*;

public class CityRepositoryTestImpl implements CityRepository{

    private final Map<UUID, City> cities = new LinkedHashMap<>();

    @Override
    public List<City> findByTravelUuidAndUserUuidOrderByTravelNumber(UUID travelUuid, UUID userUuid) {
        return List.copyOf(cities.values());
    }

    @Override
    public City findByUuidAndTravelUuidAndUserUuid(UUID uuid, UUID travelUuid, UUID userUuid) {
        return cities.get(uuid);
    }

    @Override
    public void deleteTravelCities(UUID travelUuid, UUID userUuid) {
        cities.clear();
    }

    @Override
    public void clearEmptyTravelCities(UUID travelUuid, UUID userUuid) {
        List<City> values = new ArrayList<>(cities.values());
        values.forEach(v -> {
            if(v.getPlacesCount() == 0){
                cities.remove(v.getUuid());
            }
        });
    }

    @Override
    public List<City> findAll() {
        return List.copyOf(cities.values());
    }

    @Override
    public List<City> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<City> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<City> findAllById(Iterable<UUID> uuids) {
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
    public void delete(City city) {
        cities.remove(city.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends City> iterable) {

    }

    @Override
    public void deleteAll() {
        cities.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends City> S save(S s) {
        return (S) cities.put(s.getUuid(), s);
    }

    @Override
    public <S extends City> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<City> findById(UUID uuid) {
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
    public <S extends City> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<City> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public City getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends City> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends City> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends City> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends City> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends City> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends City> boolean exists(Example<S> example) {
        return false;
    }
}