package com.virginiatech.piraj.hw5drawingapp;

import android.content.Context;
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
        //iconLongPress.setBounds(0, 0, iconLongPress.getIntrinsicWidth(), iconLongPress.getIntrinsicHeight());

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

    public void removePath(int id){
        if(activePaths.containsKey(id)){
            //activePaths.remove(id);
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

    public void drawIcon(){

        //TODO

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

        //TODO Draw path on canvas
        for(Object drawing : drawings.values()){

            if(drawing instanceof SavedPath){
                SavedPath savedPath = (SavedPath) drawing;
                canvas.drawPath(savedPath.getPath(), savedPath.getPaint());
            }


            if(drawing instanceof SavedDrawable){
                SavedDrawable drawable = (SavedDrawable) drawing;

                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
                //canvas.drawBitmap(bitmap, x, y, null);

                canvas.save();
                canvas.translate(drawable.getX(), drawable.getY());
                drawable.getIcon().draw(canvas);
                canvas.restore();

            }

        }

        for(Path path : activePaths.values()){
            canvas.drawPath(path, pathPaint);
        }

    }

}
