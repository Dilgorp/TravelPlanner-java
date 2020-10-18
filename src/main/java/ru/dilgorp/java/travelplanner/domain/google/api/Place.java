package ru.dilgorp.java.travelplanner.domain.google.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "place")
@JsonIgnoreProperties({"imagePath", "placeId"})
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private String description;
    private String placeId;
    private String imagePath;
    @Column(name = "current_page_token", length = 1024)
    private String currentPageToken;
    @Column(name = "next_page_token", length = 1024)
    private String nextPageToken;

    private UUID userRequestUUID;

    public Place() {
    }

    public Place(String name, String description, String placeId, String imagePath, String currentPageToken, String nextPageToken, UUID userRequestUUID) {
        this.name = name;
        this.description = description;
        this.placeId = placeId;
        this.imagePath = imagePath;
        this.currentPageToken = currentPageToken;
        this.nextPageToken = nextPageToken;
        this.userRequestUUID = userRequestUUID;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCurrentPageToken() {
        return currentPageToken;
    }

    public void setCurrentPageToken(String currentPageToken) {
        this.currentPageToken = currentPageToken;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public UUID getUserRequestUUID() {
        return userRequestUUID;
    }

    public void setUserRequestUUID(UUID userRequestUUID) {
        this.userRequestUUID = userRequestUUID;
    }
}
