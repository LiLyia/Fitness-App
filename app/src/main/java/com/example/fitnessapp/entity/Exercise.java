package com.example.fitnessapp.entity;

<<<<<<< HEAD
public class Exercise {
    String activityType;
    String duration;
    String distance;
    String energyExpended;
    String date;

    @Override
    public String toString() {
        return "Exercise{" +
                "activityType='" + activityType + '\'' +
                ", duration='" + duration + '\'' +
                ", distance='" + distance + '\'' +
                ", energyExpended='" + energyExpended + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public  Exercise(){

    }
    public Exercise(String activityType, String duration, String distance, String energyExpended, String date) {
        this.activityType = activityType;
        this.duration = duration;
        this.distance = distance;
        this.energyExpended = energyExpended;
        this.date = date;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEnergyExpended() {
        return energyExpended;
    }

    public void setEnergyExpended(String energyExpended) {
        this.energyExpended = energyExpended;
    }



=======
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
>>>>>>> dc4980b (scroll view and data reading fixed)
}
