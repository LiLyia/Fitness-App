package com.example.fitnessapp.entity;

import java.util.List;

public class Recipe {
    private String name = "1";
    private int priority;
    private String ingredients;

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String instruction;
    private String type = "recipe";

    public Recipe() {}
    public Recipe(String ingredients, String instruction, String name, int priority, String type) {
        this.name = name;
        this.priority = priority;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.type = type;
    }
    public String getType() {return type;}
    public String getName() {return name;}
    public int getPriority() {return priority;}
    public String getIngredients() {return ingredients;}
    public String getInstruction() {return instruction;}

}
