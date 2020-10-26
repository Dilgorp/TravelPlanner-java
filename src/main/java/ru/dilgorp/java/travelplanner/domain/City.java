package ru.dilgorp.java.travelplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "city")
@JsonIgnoreProperties({"imagePath"})
@Data
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private int placesCount;
    private String description;
    private String imagePath;

    private UUID travelUuid;
    private UUID userUuid;
    private UUID userRequestUuid;

    private int travelNumber;

    public City(
            String name, int placesCount, String description, String imagePath,
            UUID travelUuid, UUID userUuid, int travelNumber
    ) {
        this.name = name;
        this.placesCount = placesCount;
        this.description = description;
        this.imagePath = imagePath;
        this.travelUuid = travelUuid;
        this.userUuid = userUuid;
        this.travelNumber = travelNumber;
    }
}
