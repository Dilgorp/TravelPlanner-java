package ru.dilgorp.java.travelplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"imagePath"})
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private int citiesCount;
    private int placesCount;
    private String imagePath;

    private UUID userUuid;

    public Travel() {
    }

    public Travel(String name, int citiesCount, int placesCount, String imagePath, UUID userUuid) {
        this.name = name;
        this.citiesCount = citiesCount;
        this.placesCount = placesCount;
        this.imagePath = imagePath;
        this.userUuid = userUuid;
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

    public int getCitiesCount() {
        return citiesCount;
    }

    public void setCitiesCount(int citiesCount) {
        this.citiesCount = citiesCount;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }
}
