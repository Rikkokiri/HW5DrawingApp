package com.virginiatech.piraj.hw5drawingapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Activity where the actual drawing happens
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.23
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

        redColor.setOnClickListener(colourListener);
        blueColor.setOnClickListener(colourListener);
        greenColor.setOnClickListener(colourListener);

        // ----- Action buttons and listeners -----------
        clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(clearHandler);

        undoButton = (Button) findViewById(R.id.undoButton);
        undoButton.setOnClickListener(undoHandler);

        doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(doneHandler);

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
     * OnClickListener for clear button
     */
    private View.OnClickListener clearHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    /**
     * OnClickListener for undo button
     */
    private View.OnClickListener undoHandler = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

        }
    };

    /**
     * OnClickListener for done button
     */
    private View.OnClickListener doneHandler = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

        }
    };

    /**
     * Listener for colour buttons
     */
    private View.OnClickListener colourListener = new View.OnClickListener() {
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
    };

}
