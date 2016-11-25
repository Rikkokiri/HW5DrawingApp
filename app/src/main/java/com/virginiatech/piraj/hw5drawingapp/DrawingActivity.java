package com.virginiatech.piraj.hw5drawingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Activity where the actual drawing happens
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.23
 */

public class DrawingActivity extends AppCompatActivity {

    private DrawingCanvas canvas;

    private Uri imageURI;
    private String appDirectory;

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

    // --- TouchHandler ---
    private TouchHandler touchHandler;

    // --- SoundPlayer ---
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        canvas = (DrawingCanvas) findViewById(R.id.drawingCanvas);

        //Get intent that the StartActivity used to start this activity
        Intent imageIntent = getIntent();

        //Get the image URI from the intent
        Bundle extras = imageIntent.getExtras();
        imageURI = Uri.parse(extras.getString("imageURI"));

        //Set the image as the background of the canvas
        canvas.setImageURI(imageURI);

        //Get filepath for the directory used to store the images
        appDirectory = extras.getString("app_directory");

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

        // ----- TouchHandler -----------
        touchHandler = new TouchHandler(this); //Pass TouchHandler a reference to this activity
        canvas.setOnTouchListener(touchHandler);

        // ----- SoundPlayer ------------
        soundPlayer = new SoundPlayer(this);

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
            canvas.clearCanvas();
        }
    };

    /**
     * OnClickListener for undo button
     */
    private View.OnClickListener undoHandler = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            canvas.undoDraw();
        }
    };

    /**
     * OnClickListener for done button
     * When done button is pressed the image is saved on the phone and user will be taken back to
     * the StartActivity where user can start the whole picture taking and drawing process all over again.
     */
    private View.OnClickListener doneHandler = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            try {
                //canvas.saveDrawing(appDirectory);

                Bitmap drawingBitmap = canvas.getDrawing();

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                FileOutputStream fileOutputStream = new FileOutputStream(appDirectory + File.separator + "IMG_" + timestamp + ".jpg");

                drawingBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

                File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "HW5DrawingApp");

                //Add image to the gallery as well
                MediaStore.Images.Media.insertImage(getContentResolver(), drawingBitmap, "Drawing"+timestamp+".JPG", "HW5DrawingApp drawing");

                Toast.makeText(DrawingActivity.this, "Drawing was saved to your gallery", Toast.LENGTH_LONG).show();

                //Return to the StartActivity
                Intent returnIntent = new Intent(view.getContext(), StartActivity.class);
                startActivity(returnIntent);

            } catch (FileNotFoundException e) {
                Toast.makeText(DrawingActivity.this, "Error saving the picture", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
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

    /**
     * Draw a new path
     *
     * @param id Path id
     * @param x X-coordinate of the path
     * @param y Y-coordinate of the path
     */
    public void addNewPath(int id, float x, float y) {
        canvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        canvas.updatePath(id, x, y);
    }

    /**
     * User finished drawing a path
     *
     * @param id Id of the path the user finished drawing
     */
    public void pathDone(int id){
        canvas.pathDone(id);
    }

    /**
     * TODO Javadoc
     *
     * @param posX
     * @param posY
     */
    public void drawDoubleTapIcon(float posX, float posY){
        canvas.drawDoubleTapIcon(posX, posY);
    }

    /**
     * TODO Javadoc
     *
     * @param posX
     * @param posY
     */
    public void drawLongPressIcon(float posX, float posY){
        canvas.drawLongPressIcon(posX, posY);
    }

    // <o>-<o>-<o>-<o>-<o>-<o> HANDLE SOUND EFFECTS <o>-<o>-<o>-<o>-<o>-<o>

    public void playSound(){
        soundPlayer.playSound();
    }

    public void pauseSound(){
        soundPlayer.pauseSound();
    }

    public void stopSound(){
        soundPlayer.stopSound();
    }

}
