package com.example.nasaday;

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
