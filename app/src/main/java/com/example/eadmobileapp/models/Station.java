package com.example.eadmobileapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Station {

    private String _id;
    private String stationName;
    private String stationArea;
    private String stationAddress;
    private String owner;
    private boolean openClose;
    @SerializedName("fuelTypes")
    private ArrayList<fuelType> fuelTypes = null ;

    public Station(String _id, String stationName, String stationArea, String owner) {
        this._id = _id;
        this.stationName = stationName;
        this.stationArea = stationArea;
        this.owner = owner;

    }

//    public Station(String stationName, String stationAddress, String stationArea, String owner, boolean openClose) {
//        this.stationName = stationName;
//        this.stationArea = stationArea;
//        this.stationAddress = stationAddress;
//        this.owner = owner;
//        this.openClose = openClose;
//    }

    public Station(String stationName, String stationArea, String stationAddress, String owner, boolean openClose, ArrayList<fuelType> fuelTypes) {
        this.stationName = stationName;
        this.stationArea = stationArea;
        this.stationAddress = stationAddress;
        this.owner = owner;
        this.openClose = openClose;
        this.fuelTypes = fuelTypes;
    }

    public Station(String station_name, String fuel_type, String arrival_time, String fuel_litres, boolean fuel_status) {

        System.out.println("station name = " + station_name);
        System.out.println("fuel type = " + fuel_type);
        System.out.println("arrival time = " + arrival_time);
        System.out.println("fuel litres = " + fuel_litres);
        System.out.println("fuel status = " + fuel_status);

        this.stationName = station_name;
        ArrayList <fuelType> fuelTypes = new ArrayList<>();
        fuelTypes.add(new fuelType(fuel_type, arrival_time, fuel_litres, fuel_status));
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

    public static class fuelType{
        @SerializedName("fuelType")
        public String fuelType;
        @SerializedName("availability")
        public boolean availability;
        @SerializedName("nextArrival")
        public String nextArrival;
        @SerializedName("liters")
        public String litres;


        public fuelType(String fuelType, boolean availability, String nextArrival) {
            this.fuelType = fuelType;
            this.availability = availability;
            this.nextArrival = nextArrival;
        }

        public fuelType(String fuel_type, String arrival_time, String fuel_litres, boolean fuel_status) {
            this.fuelType = fuel_type;
            this.nextArrival = arrival_time;
            this.litres = fuel_litres;
            this.availability = fuel_status;
        }
        //getters and setters


        public String getFuelType() {
            return fuelType;
        }

        public boolean isAvailability() {
            return availability;
        }

        public String getNextArrival() {
            return nextArrival;
        }

        public String getLitres() {
            return litres;
        }
    }

}
