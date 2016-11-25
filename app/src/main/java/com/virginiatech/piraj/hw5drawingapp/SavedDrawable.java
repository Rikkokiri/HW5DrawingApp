package com.virginiatech.piraj.hw5drawingapp;

import android.graphics.drawable.Drawable;

/**
 * TODO Javadoc
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.24
 */
public class SavedDrawable {

    private Drawable icon;
    private float posX;
    private float posY;

    public SavedDrawable(Drawable icon, float posX, float posY){
        this.icon = icon;
        this.posX = posX;
        this.posY = posY;
    }

    public Drawable getIcon(){
        return this.icon;
    }

    public float getX(){
        return this.posX;
    }

    public float getY(){
        return this.posY;
    }
}
