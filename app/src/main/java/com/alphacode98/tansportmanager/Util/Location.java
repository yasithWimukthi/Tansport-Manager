package com.alphacode98.tansportmanager.Util;

public class Location {
    private String name;
    private float cost;
    private float distance;
    private float routeNumber;

    public Location() {
    }

    public Location(String name, float cost, float distance, float routeNumber) {
        this.name = name;
        this.cost = cost;
        this.distance = distance;
        this.routeNumber = routeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(float routeNumber) {
        this.routeNumber = routeNumber;
    }
}
