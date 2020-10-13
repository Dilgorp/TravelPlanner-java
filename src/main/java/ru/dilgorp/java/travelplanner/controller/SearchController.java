package ru.dilgorp.java.travelplanner.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Value("${google.place-api.key}")
    private String googlePlaceApiKey;

    @GetMapping("/search/city")
    public String getCityInfo(){
        return googlePlaceApiKey;
    }
}
