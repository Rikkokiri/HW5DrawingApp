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

public class StartActivity extends AppCompatActivity {

    private Button takePictureButton;
    private Uri file;

    /*
     * <o><o><o><o><o><o> OnCreate <o><o><o><o><o><o>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Button that is used to take a picture
        takePictureButton = (Button) findViewById(R.id.takePicture);

        //Check permissions, disable picture taking button if we don't have camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

    }

    //--------------- TAKE A PICTURE ------------------------

    /**
     *
     */
    public void takePicture(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            /*
            file = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
            startActivityForResult(intent, 100);
             */

        }

    }

    //--------------- STORAGE -------------------------------

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

    /*
    // Create the File where the photo should go
    File photoFile = null;
    try {
        photoFile = createImageFile();
    } catch (IOException ex) {
        // Error occurred while creating the File
        ...
    }
    // Continue only if the File was successfully created
    if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(this,
                "com.example.android.fileprovider",
                photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }
    */

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
