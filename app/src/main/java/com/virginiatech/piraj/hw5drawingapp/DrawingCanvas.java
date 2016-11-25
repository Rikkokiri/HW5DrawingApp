package com.virginiatech.piraj.hw5drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * TODO Javadoc
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.23
 */

public class DrawingCanvas extends ImageView {

    private Paint pathPaint;
    
    /**
     * Type Object is used so that both Drawable and Path objects can be stored in the map
     */
    private LinkedHashMap<Integer, Object> drawings;
    /**
     * LinkedHashMap for storing paths that are being drawn
     */
    private LinkedHashMap<Integer, Path> activePaths;

    private Drawable iconLongPress;
    private Drawable iconDoubleTap;

    /**
     * Constructor
     */
    public DrawingCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.drawings = new LinkedHashMap<Integer, Object>();
        this.activePaths = new LinkedHashMap<Integer, Path>();

        // --- Paint ---
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(40); //dp

        // --- Icons ---
        iconLongPress = ResourcesCompat.getDrawable(getResources(), R.drawable.flaming_bottle, null);
        iconDoubleTap = ResourcesCompat.getDrawable(getResources(), R.drawable.gun, null);

    }

    /**
     * Change the color of the paint
     *
     * @param color New color of the paint
     */
    public void changePaintColor(int color){
        pathPaint.setColor(color);
    }

    //Initializes a path
    public void addPath(int id, float x, float y){
        Path path = new Path();
        path.moveTo(x, y);
        activePaths.put(id, path);

        //Call onDraw()
        invalidate();
    }

    /**
     * Whenever we detect a new touch, we update the path (?)
     */
    public void updatePath(int id, float x, float y){
        Path path = null;

        if(activePaths.get(id) instanceof Path){ //TODO Remove check?
            path = (Path) activePaths.get(id);
        }

        if(path != null){
            path.lineTo(x, y);
        }
        //Call onDraw()
        invalidate();
    }

    /**
     * Remove the most recent drawing
     */
    public void undoDraw(){

        Integer lastID = null;

        for(Integer id : drawings.keySet()){
            lastID = id;
        }
        //Remove the last added drawing
        if(lastID != null){
            drawings.remove(lastID);
        }

        //Call onDraw()
        invalidate();
    }

    /**
     * Save the path once user stops drawing it.
     *
     * @param id Id value of the finished path
     */
    public void pathDone(int id){

        if(activePaths.containsKey(id)){

            PathMeasure pm = new PathMeasure(activePaths.get(id), false);

            //Check that something was actually drawn before adding the path
            if(pm.getLength() > 1) {
                drawings.put(Id.createID(), new SavedPath(activePaths.get(id), pathPaint));
            }
            activePaths.remove(id);
        }
        invalidate();
    }

    /**
     * Draw icon on double tap
     *
     * @param posX
     * @param posY
     */
    public void drawDoubleTapIcon(float posX, float posY){
        drawIcon(iconDoubleTap, posX, posY);
        invalidate();
    }

    /**
     * Draw icon on long press
     *
     * @param posX
     * @param posY
     */
    public void drawLongPressIcon(float posX, float posY){
        drawIcon(iconLongPress, posX, posY);
        invalidate();
    }

    /**
     * TODO Javadoc
     *
     * @param drawable
     * @param posX
     * @param posY
     */
    private void drawIcon(Drawable drawable, float posX, float posY){
        drawings.put(new Integer(Id.createID()), new SavedDrawable(drawable, posX, posY));
    }

    /**
     * Clear canvas
     */
    public void clearCanvas(){
        activePaths.clear();
        drawings.clear();
        invalidate();
    }

    /**
     * TODO Javadoc
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        for(Object drawing : drawings.values()){

            //Draw icon (Drawable) on canvas
            if(drawing instanceof SavedDrawable){
                SavedDrawable drawable = (SavedDrawable) drawing;

                Drawable icon = drawable.getIcon();
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

                float x = drawable.getX() - (float)(icon.getIntrinsicWidth() / 2.0);
                float y = drawable.getY() - (float)(icon.getIntrinsicHeight() / 2.0);

                canvas.save();
                canvas.translate(x, y);
                icon.draw(canvas);
                canvas.restore();
            }

            //Draw saved paths on canvas
            if(drawing instanceof SavedPath){
                SavedPath savedPath = (SavedPath) drawing;
                canvas.drawPath(savedPath.getPath(), savedPath.getPaint());
            }

        }

        //Draw the currently active path(s)
        for(Path path : activePaths.values()){
            canvas.drawPath(path, pathPaint);
        }

    }

}
