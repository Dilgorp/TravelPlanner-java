package ru.dilgorp.java.travelplanner.repository.google.api;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;

import java.util.*;

public class UserRequestRepositoryTestImpl implements UserRequestRepository{

    private final static String NO_USER_REQUEST = "83b5b123-9cac-41f6-b45c-456a84f14d90";

    private Map<UUID, UserRequest> requests = new LinkedHashMap<>();

    @Override
    public UserRequest findByText(String text) {
        UserRequest userRequest = null;
        for(UserRequest request : requests.values()){
            if(request.getText().equals(text)){
                userRequest = request;
                break;
            }
        }
        return userRequest;
    }

    @Override
    public List<UserRequest> findAll() {
        return null;
    }

    @Override
    public List<UserRequest> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserRequest> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<UserRequest> findAllById(Iterable<UUID> uuids) {
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
    public void delete(UserRequest userRequest) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserRequest> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends UserRequest> S save(S s) {
        return (S) requests.put(s.getUuid(), s);
    }

    @Override
    public <S extends UserRequest> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<UserRequest> findById(UUID uuid) {
        if(uuid.equals(UUID.fromString(NO_USER_REQUEST))){
            return Optional.empty();
        }
        return Optional.ofNullable(requests.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserRequest> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<UserRequest> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserRequest getOne(UUID uuid) {
        return requests.get(uuid);
    }

    @Override
    public <S extends UserRequest> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserRequest> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends UserRequest> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends UserRequest> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserRequest> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserRequest> boolean exists(Example<S> example) {
        return false;
    }
}