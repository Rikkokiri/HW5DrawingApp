package com.virginiatech.piraj.hw5drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.LinkedHashMap;

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

    private Drawable iconLongPress;
    private Drawable iconDoubleTap;


    /**
     * Constructor
     */
    public DrawingCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.drawings = new LinkedHashMap<Integer, Object>();

        //Create paint
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(40); //dp

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
        drawings.put(id, path);

        //Call onDraw()
        invalidate();
    }

    /**
     * Whenever we detect a new touch, we update the path (?)
     */
    public void updatePath(int id, float x, float y){
        Path path = null;

        if(drawings.get(id) instanceof Path){
            path = (Path) drawings.get(id);
        }

        if(path != null){

            path.lineTo(x, y);
        }
        //Call onDraw()
        invalidate();
    }

    public void removePath(int id){
        if(drawings.containsKey(id)){
            drawings.remove(id);
        }
        //Call onDraw()
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        //TODO Draw path on canvas
        for(Object drawing : drawings.values()){

            if(drawing instanceof Path){
                System.out.println("Draw path!");

                Path path = (Path) drawing;
                canvas.drawPath(path, pathPaint);
            }

            /*
            if(drawing instanceof Drawable){
                Drawable drawable = (Drawable) drawing;

                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
                //canvas.drawBitmap(bitmap, x, y, null);

            }*/


            //Make the change happen immediately?
            //invalidate();
        }

    }

}
