package ru.dilgorp.java.travelplanner.domain.google.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "place")
@JsonIgnoreProperties({
        "placeId",
        "currentPageToken",
        "nextPageToken",
        "userRequestUuid"
})
@Data
@NoArgsConstructor
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

    private UUID userRequestUuid;
}
