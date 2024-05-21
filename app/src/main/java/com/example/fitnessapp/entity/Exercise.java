package com.example.fitnessapp.entity;

import java.util.List;

public class Exercise {
    private String name;
    private int priority;
    private String instruction;
    private String type = "exercise";
    public Exercise() {}
    public Exercise(String instruction, String name, int priority, String type) {
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
