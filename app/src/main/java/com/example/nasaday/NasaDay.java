package com.example.nasaday;

/**
 * The model - class that represent the Nasa photo of the day information
 */
public class NasaDay {

    private String date;
    private String image;
    protected long id;

    NasaDay(String date, long i){
        this.date = date;
        id = i;
    }

    public String getDate() {
        return date;
    }

    public long getId(){
        return id;
    }
}
