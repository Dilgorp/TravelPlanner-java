package ru.dilgorp.java.travelplanner.repository.google.api;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.java.travelplanner.domain.google.api.UserRequest;

import java.util.UUID;

public interface UserRequestRepository  extends JpaRepository<UserRequest, UUID> {
    UserRequest findByText(String text);
}
