package com.example.eadmobileapp.models;

import java.util.List;

public class Queue {

    private String stationName;
    private String fuel_type;
    private Integer count;

    public Queue(String stationName, String fuel_type, Integer count) {
        this.stationName = stationName;
        this.fuel_type = fuel_type;
        this.count = count;
    }

    public String getStationName() {
        return stationName;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public Integer getCount() {
        return count;
    }
}
