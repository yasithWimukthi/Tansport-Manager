package com.alphacode98.tansportmanager.Modal;

public class Journey {
    private int journeyId;
    private int clientId;
    private int rootNo;
    private String vehicleNo;
    private String date;
    private String startTime;
    private String endTime;
    private String startLocation;
    private String endLocation;
    private String distance;
    private float fare;

    public Journey() {
    }

    public Journey(int journeyId, int clientId, int rootNo, String vehicleNo, String date, String startTime, String endTime, String startLocation, String endLocation, String distance, float fare) {
        this.journeyId = journeyId;
        this.clientId = clientId;
        this.rootNo = rootNo;
        this.vehicleNo = vehicleNo;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.fare = fare;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getRootNo() {
        return rootNo;
    }

    public void setRootNo(int rootNo) {
        this.rootNo = rootNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }
}
