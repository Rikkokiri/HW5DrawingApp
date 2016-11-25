package com.virginiatech.piraj.hw5drawingapp;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Class for saving Paths
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.25
 */

public class SavedPath {

    private Path path;
    private Paint paint;

    public SavedPath(Path path){
        this.path = path;
    }

    public SavedPath(Path path, Paint paint){
        this(path);
        this.paint = new Paint(paint); //Create copy of the paint object
    }

    public void setPaint(Paint paint){
        this.paint = paint;
    }

    public Path getPath(){
        return this.path;
    }

    public Paint getPaint(){
        return this.paint;
    }
}
