package com.example.nasaday;

/**
 * The model - class that represent the Nasa photo of the day information
 */
public class NasaDay {

    private String name;
    private String description;

    NasaDay(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
