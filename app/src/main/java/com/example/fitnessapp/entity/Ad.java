package com.example.fitnessapp.entity;

public class Ad {
    private String name;
    private int priority;
    private String description;
    private String type = "ad";
    private String image;
    public Ad() {}
    public Ad(String description, String name, int priority, String type) {
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.type = type;
    }
    public String getName() {return name;}
    public int getPriority() {return priority;}
    public String getDescription() {return description;}
    public String getType() {return type;}
}
