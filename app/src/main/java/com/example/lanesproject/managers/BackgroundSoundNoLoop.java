package com.example.lanesproject.managers;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class BackgroundSoundNoLoop extends AsyncTask<Void, Void, Void> {

    private Context context;
    private int id;

    public BackgroundSoundNoLoop(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // define that params are 1 - id, 2 - isLoop
        makeSoundNoLoop();
        return null;
    }

    public void makeSoundNoLoop(){
        MediaPlayer player = MediaPlayer.create(this.context, id);
        player.setLooping(false); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();
    }

}
