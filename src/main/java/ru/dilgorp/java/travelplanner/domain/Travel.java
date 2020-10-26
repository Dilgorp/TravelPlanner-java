package ru.dilgorp.java.travelplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"imagePath"})
@Data
@NoArgsConstructor
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private int citiesCount;
    private int placesCount;
    private String imagePath;

    private UUID userUuid;

    public Travel(String name, int citiesCount, int placesCount, String imagePath, UUID userUuid) {
        this.name = name;
        this.citiesCount = citiesCount;
        this.placesCount = placesCount;
        this.imagePath = imagePath;
        this.userUuid = userUuid;
    }
}
