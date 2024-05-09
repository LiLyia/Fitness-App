package com.example.fitnessapp.entity;

import java.util.List;

public class Exercise {
    private String name;
    private int priority;
    private List<String> instructions;
    private List<String> images;
    private String type = "exercise";
    public Exercise() {}
    public Exercise(String name, int priority, List<String> instructions, List<String> images) {
        this.name = name;
        this.priority = priority;
        this.instructions = instructions;
        this.images = images;
    }
    public String getName() {return name;}
    public int getPriority() {return priority;}
    public List<String> getInstructions() {return instructions;}
    public List<String> getImages() {return images;}
    public String getType() {return type;}
}
