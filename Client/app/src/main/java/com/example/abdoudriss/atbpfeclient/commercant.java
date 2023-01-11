package com.example.abdoudriss.atbpfeclient;

/**
 * Created by Abdou Driss on 16/05/2017.
 */

public class commercant {
    private String Langitude;
    private String Latitude;
    private String work;

    public String getLangitude() {
        return Langitude;
    }

    public void setLangitude(String langitude) {
        Langitude = langitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public commercant(String langitude, String latitude, String work) {
        Langitude = langitude;
        Latitude = latitude;
        this.work = work;
    }

    public commercant() {
    }
}