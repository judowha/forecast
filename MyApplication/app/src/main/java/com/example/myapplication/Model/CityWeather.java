package com.example.myapplication.Model;

import java.util.ArrayList;

public class CityWeather {
    private Coord coord;
    private ArrayList<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Cloud cloud;
    private Rain rain  ;
    private Snow snow;
    private long dt;
    //private Sys sys;
    private long timezone;
    private Long id;
    private String name;
    private int cod;


    public Coord getCoord() {
        return coord;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public long getDt() {
        return dt;
    }

    public long getTimezone() {
        return timezone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }


    @Override
    public String toString() {
        return "CityWeather{" +
                "coord=" + coord +
                ", weathers=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", cloud=" + cloud +
                ", rain=" + rain +
                ", snow=" + snow +
                ", dt=" + dt +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}
