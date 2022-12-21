package com.example.lanesproject.managers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lanesproject.activities.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    // this class purpose is to manage the sounds running on the application
    // if an activity is closed it's the activity job to tell the manager
    // to close the sound as well, or to find a solution for keeping the
    // track over the sound that it has been activated in another way.

    // the sound manager can activate a sound when it has the id of the sound.
    // the sound manager can activate the sound in loop or not in a loop
    // the sound manager keeps track of it's sounds
    private static int counter = 0;
    private static SoundManager _instance = null;
    private AsyncTask<Void, Void, Void> sound = null;

    // a map has id:sound key value pair, to track the sounds on the manager
    private HashMap<Integer, AsyncTask<Void, Void, Void>> map = new HashMap<>();
    private int mainSoundId = 1;

    private SoundManager(){

    }

    public static synchronized void init(){
        if ( _instance == null ) {
            _instance = new SoundManager();
        }
    }

    public static synchronized  SoundManager getInstance(){
        return _instance;
    }

    public int makeSoundInLoop(Context context, int id){
        SoundManager.counter += 1;

        AsyncTask<Void, Void, Void> sound = new BackgroundSoundLoop(context, id);
        sound.execute();
        this.map.put(counter, sound);
        return counter;
    }
    public int makeSoundNotInLoop(Context context, int id){
        SoundManager.counter += 1;

        AsyncTask<Void, Void, Void> sound = new BackgroundSoundNoLoop(context, id);
        sound.execute();
        this.map.put(counter, sound);
        return counter;
    }
    public void stopSound(int num){
        this.map.get(num).cancel(true);
    }

    public void stopAllSounds(){
        for (Map.Entry<Integer, AsyncTask<Void, Void, Void>> entry: this.map.entrySet()) {
            entry.getValue().cancel(true);
        }
    }

    public void stopMainSound() {
        this.stopSound(this.mainSoundId);
    }

    public void startMainSound(Context context, int id){
        this.mainSoundId = makeSoundInLoop(context, id);
    }
}

