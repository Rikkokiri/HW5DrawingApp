package com.virginiatech.piraj.hw5drawingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity where the actual drawing happens
 */

public class DrawingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        //Get intent that the StartActivity used to start this activity
        Intent imageIntent = getIntent();

        //Get the image URI from the intent
        Bundle extras = imageIntent.getExtras();
        Uri imageURI = Uri.parse(extras.getString("imageURI"));

        System.out.println("DrawingActivity: image URI received " + imageURI.toString()); //Just for testing

    }
}
