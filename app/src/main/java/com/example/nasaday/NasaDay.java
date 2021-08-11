package com.example.nasaday;

/**
 * The model - class that represent the Nasa photo of the day information
 */
public class NasaDay {

    private String title;
    private String date;
    private byte[] image;
    protected long id;

    //default constructor
    NasaDay(){

    }

    NasaDay(String title, String date, byte[] image, long i){
        this.title = title;
        this.date = date;
        this.image = image;
        id = i;
    }

    public String getTitle(){return title;}

    public void setTitle(String title){this.title=title;}

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public byte[] getImage(){
        return image;
    }

    public void setImage(byte[] image){
        this.image = image;
    }

    public long getId(){
        return id;
    }
}
