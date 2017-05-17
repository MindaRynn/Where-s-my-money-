package com.example.mindaryn.finalproject;

import android.util.Log;

/**
 * Created by MindaRyn on 5/17/2017 AD.
 */

public class FixedCostItem {
    private String name;

    public FixedCostItem(String name){
        this.name = name;
        Log.d("fix","come in cons");
    }

    public FixedCostItem(){
        Log.d("fix","come in populate");
    }

    public String getName(){ return this.name; }

    public void setName(String newName){ this.name = newName; }
}
