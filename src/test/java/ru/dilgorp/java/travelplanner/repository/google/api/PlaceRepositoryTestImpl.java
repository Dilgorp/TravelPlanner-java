package ru.dilgorp.java.travelplanner.repository.google.api;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.travelplanner.domain.google.api.Place;

import java.util.*;
import java.util.stream.Collectors;

public class PlaceRepositoryTestImpl implements PlaceRepository {

    private final static String MORE_THEN_OPTIONS = "83b5b123-9cac-41f6-b45c-456a84f14d89";

    private final Map<UUID, Place> places = new LinkedHashMap<>();

    @Override
    public List<Place> findByUserRequestUuid(UUID userRequestUuid) {
        return places.values().stream()
                .filter(v -> v.getUserRequestUuid().equals(userRequestUuid))
                .collect(Collectors.toList());
    }

    @Override
    public int findCountByUserRequestUuid(UUID userRequestUUID) {
        if (userRequestUUID.equals(UUID.fromString(MORE_THEN_OPTIONS))) {
            return 100;
        }
        return places.size();
    }

    @Override
    public List<Place> findByUserRequestUuidAndCurrentPageToken(UUID requestUuid, String pageToken) {
        return places.values().stream()
                .filter(v ->
                        v.getUserRequestUuid().equals(requestUuid)
                                && v.getCurrentPageToken().equals(pageToken))
                .collect(Collectors.toList());
    }

    @Override
    public List<Place> findAll() {
        return List.copyOf(places.values());
    }

    @Override
    public List<Place> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Place> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Place> findAllById(Iterable<UUID> uuids) {
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
    public void delete(Place place) {

    }

    @Override
    public void deleteAll(Iterable<? extends Place> iterable) {

    }

    @Override
    public void deleteAll() {
        places.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Place> S save(S s) {
        if (s.getUuid() == null) {
            s.setUuid(UUID.randomUUID());
        }
        return (S) places.put(s.getUuid(), s);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Place> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> {
            if (e.getUuid() == null) {
                e.setUuid(UUID.randomUUID());
            }
            places.put(e.getUuid(), e);
        });
        return (List<S>) List.copyOf(places.values());
    }

    @Override
    public Optional<Place> findById(UUID uuid) {
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
    public <S extends Place> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Place> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Place getOne(UUID uuid) {
        return places.get(uuid);
    }

    @Override
    public <S extends Place> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Place> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Place> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Place> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Place> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Place> boolean exists(Example<S> example) {
        return false;
    }
}