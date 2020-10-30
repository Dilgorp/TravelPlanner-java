package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.travelplanner.domain.Travel;

import java.util.*;

public class TravelRepositoryTestImpl implements TravelRepository {

    private final Map<UUID, Travel> travels = new LinkedHashMap<>();

    @Override
    public Travel findByUuidAndUserUuid(UUID uuid, UUID userUuid) {
        return travels.get(uuid);
    }

    @Override
    public List<Travel> findByUserUuid(UUID userUuid) {
        return findAll();
    }

    @Override
    public void clearEmptyTravels(UUID userUuid) {
        travels.clear();
    }

    @Override
    public List<Travel> findAll() {
        return List.copyOf(travels.values());
    }

    @Override
    public List<Travel> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Travel> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Travel> findAllById(Iterable<UUID> uuids) {
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
    public void delete(Travel travel) {
        travels.remove(travel.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends Travel> iterable) {

    }

    @Override
    public void deleteAll() {
        travels.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Travel> S save(S s) {
        return (S) travels.put(s.getUuid(), s);
    }

    @Override
    public <S extends Travel> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Travel> findById(UUID uuid) {
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
    public <S extends Travel> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Travel> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Travel getOne(UUID uuid) {
        return travels.get(uuid);
    }

    @Override
    public <S extends Travel> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Travel> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Travel> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Travel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Travel> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Travel> boolean exists(Example<S> example) {
        return false;
    }
}