package ru.dilgorp.java.travelplanner.domain.google.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity(name = "usr_request")
@JsonIgnoreProperties({"imagePath", "expired"})
@Data
@NoArgsConstructor
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String text;

    private String formattedAddress;
    private String name;

    private String imagePath;
    private Date expired;
}
