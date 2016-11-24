package com.virginiatech.piraj.hw5drawingapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

/**
 * Activity where the actual drawing happens
 */

public class DrawingActivity extends AppCompatActivity {

    private DrawingCanvas canvas;

    // --- Color Buttons ---
    private Button redColor;
    private Button blueColor;
    private Button greenColor;

    //--- HEX values for colors ---
    private String red = "#ff0000";
    private String blue = "#0000ff";
    private String green = "#008000";

    // --- Action Buttons ---
    private Button clearButton;
    private Button undoButton;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        canvas = (DrawingCanvas) findViewById(R.id.drawingCanvas);

        //Fill the screen with the canvas
        //canvas.setScaleType(ImageView.ScaleType.FIT_XY);

        //Get intent that the StartActivity used to start this activity
        Intent imageIntent = getIntent();

        //Get the image URI from the intent
        Bundle extras = imageIntent.getExtras();
        Uri imageURI = Uri.parse(extras.getString("imageURI"));

        //Set the image as the background of the canvas
        canvas.setImageURI(imageURI);

        // ----- Colour buttons and listeners -----------

        redColor = (Button) findViewById(R.id.red);
        blueColor = (Button) findViewById(R.id.blue);
        greenColor = (Button) findViewById(R.id.green);

        ColourListener colourListener = new ColourListener();
        redColor.setOnClickListener(colourListener);
        blueColor.setOnClickListener(colourListener);
        greenColor.setOnClickListener(colourListener);

        // ----- Action buttons and listeners -----------

    }

    /**
     * Change the color of the paint
     *
     * @param color New paint color
     */
    public void changePaintColor(int color){
        canvas.changePaintColor(color);
    }

    /**
     * Listener for colour buttons
     */
    public class ColourListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.red:
                    changePaintColor(Color.parseColor(red));
                    break;
                case R.id.blue:
                    changePaintColor(Color.parseColor(blue));
                    break;
                case R.id.green:
                    changePaintColor(Color.parseColor(green));
                    break;
            }
        }
    }

}
