package com.example.eadmobileapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Station {

    private String _id,stationName,stationArea,stationAddress,owner;
    private boolean openClose;
    @SerializedName("fuelTypes")
    private ArrayList<fuelType> fuelTypes = null ;

    public Station(String _id, String stationName, String stationArea, String owner) {
        this._id = _id;
        this.stationName = stationName;
        this.stationArea = stationArea;
        this.stationAddress = stationAddress;
        this.owner = this.owner;
        this.openClose = openClose;
    }

    public Station(String stationName, String stationAddress, String stationArea, String owner, boolean openClose) {
        this.stationName = stationName;
        this.stationArea = stationArea;
        this.stationAddress = stationAddress;
        this.owner = owner;
        this.openClose = openClose;
    }

    public Station(String stationName, String stationArea, String stationAddress, String owner, boolean openClose, ArrayList<fuelType> fuelTypes) {
        this.stationName = stationName;
        this.stationArea = stationArea;
        this.stationAddress = stationAddress;
        this.owner = owner;
        this.openClose = openClose;
        this.fuelTypes = fuelTypes;
    }

    public String get_id() {
        return _id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationArea() {
        return stationArea;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isOpenClose() {
        return openClose;
    }

    public ArrayList<fuelType> getFuelTypes() {
        return fuelTypes;
    }

    public class fuelType{
        @SerializedName("fuleType")
        public String fuelType;
        @SerializedName("availability")
        public boolean availability;

        public fuelType(String fuelType, boolean availability) {
            this.fuelType = fuelType;
            this.availability = availability;
        }
        //getters and setters


    }

}
