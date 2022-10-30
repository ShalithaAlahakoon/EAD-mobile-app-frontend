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
        this.owner = owner;

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

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setStationArea(String stationArea) {
        this.stationArea = stationArea;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOpenClose(boolean openClose) {
        this.openClose = openClose;
    }

    public void setFuelTypes(ArrayList<fuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
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
