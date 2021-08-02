package com.example.nasaday;

/**
 * The model - class that represent the Nasa photo of the day information
 */
public class NasaDay {

    private String date;
    private String description;

    NasaDay(String date, String description){
        this.date = date;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
