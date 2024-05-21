package com.example.fitnessapp.entity;

public class Ad {
    private String name;
    private int priority;
    private String instruction;
    private String type = "ad";
    private String image;
    public Ad() {}
    public Ad(String instruction, String name, int priority, String type) {
        this.instruction = instruction;
        this.name = name;
        this.priority = priority;
        this.type = type;
    }
    public String getName() {return name;}
    public int getPriority() {return priority;}
    public String getInstruction() {return instruction;}
    public String getType() {return type;}
}
