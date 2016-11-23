package com.virginiatech.piraj.hw5drawingapp;

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
    }
}
