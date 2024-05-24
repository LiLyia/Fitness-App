package com.example.fitnessapp.entity;

public class Hydration {

    public Hydration(String hydration) {
        this.amount = hydration;
    }

    public Hydration() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String hydration) {
        this.amount = hydration;
    }

    String amount;

}
