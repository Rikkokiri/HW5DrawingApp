package com.virginiatech.piraj.hw5drawingapp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * TODO Javadoc
 *
 * @author Pilvi Rajala (piraj)
 * @version 2016.11.25
 */

public class SoundPlayer {

    private SoundPool soundpool;
    private int soundID;
    private boolean mediaLoaded = false;
    private boolean soundPlaying = false;
    private float mediaVolume;

    private AudioManager audioManager;
    private Activity activity;

    public SoundPlayer(Activity activity){

        this.activity = activity;

        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        float actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mediaVolume = actVolume / maxVolume;

        //Hardware buttons setting to adjust the media sound
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //Handle different SDK versions
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundpool = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(6).build();
        } else {
            //noinspection deprecation
            soundpool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mediaLoaded = true;
            }
        });
        soundID = soundpool.load(activity, R.raw.pencilsound, 1);

    }

    /**
     * Play sound
     */
    public void playSound(){
        //Has sound been loaded & is it already playing?
        if(mediaLoaded && !soundPlaying){
            // The sound will play for ever if we put the loop parameter -1
            soundpool.play(soundID, mediaVolume, mediaVolume, 1, -1, 1f); //priority, looping sound, rate
            soundPlaying = true;
        }
    }

    /**
     * Pause sound
     */
    public void pauseSound() {
        if (soundPlaying) {
            soundpool.pause(soundID);
            soundID = soundpool.load(activity, R.raw.pencilsound, 1);
            soundPlaying = false;
        }
    }

    /**
     * Stop sound
     */
    public void stopSound() {
        if (soundPlaying) {
            soundpool.stop(soundID);
            soundID = soundpool.load(activity, R.raw.pencilsound, 1);
            soundPlaying = false;
        }
    }

}
