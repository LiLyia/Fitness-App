package com.example.fitnessapp.entity;

import java.util.List;

public class Recipe {
    private String name;
    private int priority;
    private String ingredients;
    private String instruction;
    private String type = "recipe";

    public Recipe() {}
    public Recipe(String name, int priority, String ingredients, String instruction) {
        this.name = name;
        this.priority = priority;
        this.ingredients = ingredients;
        this.instruction = instruction;
    }
    public String getType() {return type;}
    public String getName() {return name;}
    public int getPriority() {return priority;}
    public String getIngredients() {return ingredients;}
    public String getInstruction() {return instruction;}

}
