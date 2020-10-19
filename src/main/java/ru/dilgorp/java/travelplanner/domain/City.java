package ru.dilgorp.java.travelplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "city")
@JsonIgnoreProperties({"imagePath"})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private int placesCount;
    private String description;
    private String imagePath;

    private UUID travelUuid;

    public City() {}

    public City(String name, int placesCount, String description, String imagePath, UUID travelUuid) {
        this.name = name;
        this.placesCount = placesCount;
        this.description = description;
        this.imagePath = imagePath;
        this.travelUuid = travelUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public UUID getTravelUuid() {
        return travelUuid;
    }

    public void setTravelUuid(UUID travelUuid) {
        this.travelUuid = travelUuid;
    }
}
