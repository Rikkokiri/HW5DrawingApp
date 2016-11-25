package com.virginiatech.piraj.hw5drawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.LinkedHashMap;

import static android.R.attr.width;
import static com.virginiatech.piraj.hw5drawingapp.R.attr.height;

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

    private boolean clearCanvas;

    /**
     * Constructor
     */
    public DrawingCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.drawings = new LinkedHashMap<Integer, Object>();
        this.activePaths = new LinkedHashMap<Integer, Path>();

        clearCanvas = false;

        //Create paint
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(40); //dp

        iconLongPress = ResourcesCompat.getDrawable(getResources(), R.drawable.flaming_bottle, null);
        iconLongPress.setBounds(0, 0, iconLongPress.getIntrinsicWidth(), iconLongPress.getIntrinsicHeight());

        iconDoubleTap = ResourcesCompat.getDrawable(getResources(), R.drawable.gun, null);
        iconDoubleTap.setBounds(0, 0, iconDoubleTap.getIntrinsicWidth(), iconDoubleTap.getIntrinsicHeight());

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

    public void removePath(int id){
        if(activePaths.containsKey(id)){
            //activePaths.remove(id);
        }
        //Call onDraw()
        invalidate();
    }

    /**
     * TODO Javadoc
     */
    public void undoDraw(){
        Iterator<Object> iterator = drawings.values().iterator();
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
            drawings.put(Id.createID(), new SavedPath(activePaths.get(id), pathPaint));
            activePaths.remove(id);
        }
        invalidate();
    }

    public void drawDoubleTapIcon(float posX, float posY){
        drawIcon(iconDoubleTap, posX, posY);
    }

    public void drawLongPressIcon(float posX, float posY){
        drawIcon(iconLongPress, posX, posY);
    }

    /**
     * TODO Javadoc
     *
     * @param drawable
     * @param posX
     * @param posY
     */
    private void drawIcon(Drawable drawable, float posX, float posY){
        drawings.put(Id.createID(), new SavedDrawable(drawable, posX, posY));
        invalidate();
    }

    /**
     * Clear canvas
     */
    public void clearCanvas(){
        this.clearCanvas = true;
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

        //Clear canvas
        if(clearCanvas){
            clearCanvas = false;
            activePaths.clear();
            drawings.clear();
        }

        for(Object drawing : drawings.values()){

            //Draw saved paths on canvas
            if(drawing instanceof SavedPath){
                SavedPath savedPath = (SavedPath) drawing;
                canvas.drawPath(savedPath.getPath(), savedPath.getPaint());
            }

            //Draw icon (Drawable) on canvas
            if(drawing instanceof SavedDrawable){
                SavedDrawable drawable = (SavedDrawable) drawing;

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flaming_bottle);
                canvas.drawBitmap(bitmap, drawable.getX(), drawable.getY(), null);

                /*
                canvas.save();
                canvas.translate(drawable.getX(), drawable.getY());
                drawable.getIcon().draw(canvas);
                canvas.restore();
                */

            }

        }

        //Draw the currently active path(s)
        for(Path path : activePaths.values()){
            canvas.drawPath(path, pathPaint);
        }

    }

}
