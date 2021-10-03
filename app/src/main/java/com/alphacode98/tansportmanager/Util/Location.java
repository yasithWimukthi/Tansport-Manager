package com.alphacode98.tansportmanager.Util;

public class Location {
    private String name;
    private float cost;
    private float distance;
    private float routeName;

    public Location() {
    }

    public Location(String name, float cost, float distance, float routeName) {
        this.name = name;
        this.cost = cost;
        this.distance = distance;
        this.routeName = routeName;
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

    public float getRouteName() {
        return routeName;
    }

    public void setRouteName(float routeName) {
        this.routeName = routeName;
    }
}
