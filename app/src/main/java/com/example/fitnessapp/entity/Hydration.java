package com.example.fitnessapp.entity;

import java.io.Serializable;

public class Hydration implements Serializable {

    public Hydration(String hydration) {
        this.amount = hydration;
    }

    public Hydration() {
    }

    public Hydration(String hydrationTypeTxt, String hydrationDateTxt, String hydrationAmountTxt) {
        this.hydrationDate = hydrationDateTxt;
        this.amount  = hydrationAmountTxt;
        this.hydrationType = hydrationTypeTxt;

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String hydration) {
        this.amount = hydration;
    }

    String amount;

    public String getHydrationDate() {
        return hydrationDate;
    }

    public void setHydrationDate(String hydrationDate) {
        this.hydrationDate = hydrationDate;
    }

    public String getHydrationType() {
        return hydrationType;
    }

    public void setHydrationType(String hydrationType) {
        this.hydrationType = hydrationType;
    }

    String hydrationDate;
String hydrationType;
}
