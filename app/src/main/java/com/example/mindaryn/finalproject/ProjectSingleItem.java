package com.example.mindaryn.finalproject;

/**
 * Created by MindaRyn on 5/12/2017 AD.
 */

public class ProjectSingleItem {
    private String name, current, goal;

    public ProjectSingleItem(String name, String current, String goal) {
        this.name = name;
        this.current = current;
        this.goal = goal;
    }

    public ProjectSingleItem(){}

    public String getName(){ return this.name;}

    public String getCurrent(){
        return this.current+"";
    }

    public String getGoal(){
        return this.goal+"";
    }

    public void setName(String newName){ this.name = newName; }

    public void addCurrent(int money){ this.current = (Integer.parseInt(this.current) + money)+""; }

    public void removeCurrent(int money){ this.current = (Integer.parseInt(this.current) - money)+""; }

    public void setGoal(String newGoal){ this.goal = newGoal; }
}
