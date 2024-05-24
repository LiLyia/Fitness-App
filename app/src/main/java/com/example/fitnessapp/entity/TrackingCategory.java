package com.example.fitnessapp.entity;

public class TrackingCategory {
    public TrackingCategory(){
    };
    public TrackingCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}
