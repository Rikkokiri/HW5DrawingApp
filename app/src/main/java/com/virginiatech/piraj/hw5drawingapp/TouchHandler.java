package com.virginiatech.piraj.hw5drawingapp;

import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO Javadoc
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.23
 */

public class TouchHandler implements View.OnTouchListener {

    private DrawingActivity drawingActivity;
    private GestureDetectorCompat gestureDetectorCompat;

    /**
     * Constructor takes reference to the DrawingActivity
     *
     * @param drawingActivity Reference to the DrawingActivity
     */
    public TouchHandler(DrawingActivity drawingActivity){
        this.drawingActivity = drawingActivity;

        gestureDetectorCompat = new GestureDetectorCompat(this.drawingActivity, new MyGestureListener());
    }

    /**
     * Handle touch events
     *
     * TODO The app must enable single touch drawing, as well as multi-touch drawing
     * (show multiple lines while user moves multiple fingers on the screen).
     *
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int action = motionEvent.getAction();

        //Added in step 4/5
        gestureDetectorCompat.onTouchEvent(motionEvent);

        switch(action & MotionEvent.ACTION_MASK){
            //This two cases will result into the same action
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //How many touch points the user activated? Multi-touch or single touch?
                for(int i = 0, size = motionEvent.getPointerCount(); i < size; i++){
                    int id = motionEvent.getPointerId(i);
                    drawingActivity.addNewPath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;

            case MotionEvent.ACTION_MOVE:
                for(int i = 0, size = motionEvent.getPointerCount(); i < size; i++){
                    int id = motionEvent.getPointerId(i);
                    drawingActivity.updatePath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                for(int i = 0, size = motionEvent.getPointerCount(); i < size; i++){

                    //TODO ?
                    int id = motionEvent.getPointerId(i);
                    //drawingActivity.removePath(id);
                    drawingActivity.pathDone(id);
                }
                break;
        }


        //TODO Explain
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {

        /*
         * Whether or not you use GestureDetector.OnGestureListener, it's best practice to implement
         * an onDown() method that returns true. This is because all gestures begin with an onDown()
         * message. If you return false from onDown(), as GestureDetector.SimpleOnGestureListener
         * does by default, the system assumes that you want to ignore the rest of the gesture,
         * and the other methods of GestureDetector.OnGestureListener never get called. This has
         * the potential to cause unexpected problems in your app. The only time you should return
         * false from onDown() is if you truly want to ignore an entire gesture.
         */
        @Override
        public boolean onDown(MotionEvent event) {
            Log.d("TouchHandler", "onDown: " + event.toString());
            return true;
        }

        /*
         * TODO onLongPress and onDoubleTap
         *
         * The app must support adding icons by double tapping and long pressing.
         * Double tapping the screen once must add one icon. Long pressing must add another different icon.
         * The icon must be a drawable image. E.g. a star, a hokiebird, VT logo or
         * an avatar of your favorite cartoon character.
         */

        @Override
        public void onLongPress(MotionEvent event){

            System.out.println("****** LONG PRESS ******");

            //TODO drawingActivity.onLongPress(event);
            float x = event.getX();
            float y = event.getY();




            super.onLongPress(event);
        }

        @Override
        public boolean onDoubleTap(MotionEvent event){

            System.out.println("****** DOUBLE TAP ******");

            //TODO drawingActivity.onDoubleTap();

            return super.onDoubleTap(event);
        }
    }
}
