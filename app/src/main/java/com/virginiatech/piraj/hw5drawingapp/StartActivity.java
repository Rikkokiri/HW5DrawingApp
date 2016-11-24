package com.virginiatech.piraj.hw5drawingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO Javadoc
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.23
 */

public class StartActivity extends AppCompatActivity {

    private Button takePictureButton;
    private Uri imageURI;

    private final int TAKE_PHOTO_CODE = 666;
    private final static String IMAGE_INTENT = "imageURI";

    /*
     * <o><o><o><o><o><o> OnCreate <o><o><o><o><o><o>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Button that is used to take a picture
        takePictureButton = (Button) findViewById(R.id.takePicture);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        //Check permissions, disable picture taking button if we don't have camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

    }

    //--------------- TAKE A PICTURE ------------------------

    /**
     *
     */
    public void takePicture(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            imageURI = Uri.fromFile(createImageFile());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);

            startActivityForResult(takePictureIntent, TAKE_PHOTO_CODE);
        }

    }

    //--------------- onActivityResult -------------------------------

    /**
     * Start DrawingActivity and pass activity the image URI
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {

            System.out.println("IMAGE FILE CREATED:" + imageURI.toString()); //Just for testing

            //Create intent for starting DrawingActivity
            Intent startDrawingIntent = new Intent(this, DrawingActivity.class);

            //Pass the URI saved to imageFile to the activity
            startDrawingIntent.putExtra(IMAGE_INTENT, imageURI.toString());

            //Start the DrawingActivity
            startActivity(startDrawingIntent);
        }
    }

    //--------------- STORAGE -------------------------------

    /**
     * Create a file reference that the image data will be saved to
     *
     * @return A file reference the image data will be saved to
     *         Null if creating the reference failed
     */
    private File createImageFile(){

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "HW5DrawingApp");

        if(!storageDir.exists()){
            if(!storageDir.mkdirs()){
                Log.d("HW5DrawingApp", "Failed to create directory");
                return null;
            }
        }

        //Use timestamp to create a unique filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //Create and return imagefile
        return new File(storageDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg");
    }


    //--------------- PERMISSIONS ---------------------------

    /**
     * If user grants us permissions to use the camera, enable the picture taking button
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }
}
