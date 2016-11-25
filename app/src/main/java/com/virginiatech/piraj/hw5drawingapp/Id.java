package com.virginiatech.piraj.hw5drawingapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Class for creating unique ID values
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.24
 */
public class Id {

    private static Random rnd = new Random();

    public static int createID(){
        Date now = new Date();
        int id = rnd.nextInt(100) * Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }
}
