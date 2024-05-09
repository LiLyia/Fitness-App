package com.example.fitnessapp.entity;

import java.util.List;

public class Exercise {
    private String name;
    private int priority;
    private String instruction;
    private List<String> images;
    private String type = "exercise";
    public Exercise() {}
    public Exercise(String instruction, String name, int priority, String type) {
        this.name = name;
        this.priority = priority;
        this.instruction = instruction;
        this.type = type;
    }
    public void loadImages(String path) {

    }
    public String getName() {return name;}
    public int getPriority() {return priority;}
    public String getInstruction() {return instruction;}
    public List<String> getImages() {return images;}
    public String getType() {return type;}
}
