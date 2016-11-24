package com.virginiatech.piraj.hw5drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Pilvi on 23/11/16.
 */

public class DrawingCanvas extends ImageView {

    private Paint pathPaint;

    /**
     * Constructor
     */
    public DrawingCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        //Create paint
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(40); //dp

    }

    /**
     * Change the color of the paint
     *
     * @param color New color of the paint
     */
    public void changePaintColor(int color){
        pathPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //TODO Draw path on canvas

    }

}
