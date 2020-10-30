package ru.dilgorp.java.travelplanner.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.travelplanner.domain.User;

import java.util.*;

public class UserRepositoryTestImpl implements UserRepository {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        User user = null;

        Optional<User> first = users.values().stream()
                .filter(u -> u.getUsername().equals(username)).findFirst();
        if(first.isPresent()){
            user = first.get();
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public List<User> findAll(Sort sort) {
        return List.copyOf(users.values());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<UUID> uuids) {
        List<User> list = new ArrayList<>();
        uuids.forEach(uuid -> {
            if (users.containsKey(uuid)) {
                list.add(users.get(uuid));
            }
        });
        return list;
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        iterable.forEach(user -> users.remove(user.getUuid()));
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends User> S save(S s) {
        return (S) users.put(s.getUuid(), s);
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(UUID uuid) {
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
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }
}
