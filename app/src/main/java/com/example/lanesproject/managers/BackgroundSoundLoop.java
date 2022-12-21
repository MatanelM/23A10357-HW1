package com.example.lanesproject.managers;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.util.ArrayList;

public class BackgroundSoundLoop extends AsyncTask<Void, Void, Void> {

    private Context context;
    private int id;

    public BackgroundSoundLoop(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // define that params are 1 - id, 2 - isLoop
        makeSoundInLoop();
        return null;
    }

    public void makeSoundInLoop(){
        MediaPlayer player = MediaPlayer.create(this.context, id);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();
    }

}
