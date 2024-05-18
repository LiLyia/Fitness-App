package com.example.fitnessapp.entity;

public class Meal {


    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(String caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }
    String mealType;
    String caloriesConsumed;
    String carbs;
    String protein;

    public Meal() {
    }

    public Meal(String mealType, String caloriesConsumed, String carbs, String protein, String sugar) {
        this.mealType = mealType;
        this.caloriesConsumed = caloriesConsumed;
        this.carbs = carbs;
        this.protein = protein;
        this.sugar = sugar;
    }

    String sugar;


}
