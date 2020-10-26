package ru.dilgorp.java.travelplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "city_place")
@JsonIgnoreProperties({"imagePath"})
@Data
@NoArgsConstructor
public class CityPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private String description;
    private String imagePath;
    private UUID cityUuid;
    private UUID travelUuid;
    private UUID userUuid;

    public CityPlace(String name, String description, String imagePath, UUID cityUuid, UUID travelUuid, UUID userUuid) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.cityUuid = cityUuid;
        this.travelUuid = travelUuid;
        this.userUuid = userUuid;
    }
}
