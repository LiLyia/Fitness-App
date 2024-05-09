package com.example.fitnessapp.entity;

public class Ad {
    private String name;
    private int priority;
    private String description;
    private String type = "ad";
    public Ad() {}
    public Ad(String name, int priority, String description) {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }
}
