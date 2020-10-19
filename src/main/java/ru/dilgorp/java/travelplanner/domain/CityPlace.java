package ru.dilgorp.java.travelplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "city_place")
@JsonIgnoreProperties({"imagePath", "cityUuid", "travelUuid"})
public class CityPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private String description;
    private String imagePath;
    private UUID cityUuid;
    private UUID travelUuid;

    public CityPlace() {
    }

    public CityPlace(String name, String description, String imagePath, UUID cityUuid, UUID travelUuid) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.cityUuid = cityUuid;
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

    public UUID getCityUuid() {
        return cityUuid;
    }

    public void setCityUuid(UUID cityUuid) {
        this.cityUuid = cityUuid;
    }

    public UUID getTravelUuid() {
        return travelUuid;
    }

    public void setTravelUuid(UUID travelUuid) {
        this.travelUuid = travelUuid;
    }
}
