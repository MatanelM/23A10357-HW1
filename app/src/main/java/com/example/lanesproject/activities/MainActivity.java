package com.example.lanesproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lanesproject.R;
import com.example.lanesproject.entities.Player;
import com.example.lanesproject.managers.GameManager;
import com.example.lanesproject.managers.GsonManager;
import com.example.lanesproject.managers.LocationManager;
import com.example.lanesproject.managers.SoundManager;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final String SP_KEY_GAME_MANAGER = "SP_KEY_GAME_MANAGER";

    private GameManager gameManager;
    private int spaceshipLeft =0;
    boolean isHitThisTime = false;

    private ImageView[][] asteroids;
    private ImageView leftBtn;
    private ImageView rightBtn;
    private ImageView spaceship;

    private ImageView[] spaceships;

    private ImageView[] gameEngines;
    final Handler handler = new Handler(Looper.myLooper());
    private Runnable gameRunnable = new Runnable() {
        @Override
        public void run() {
            if ( gameManager == null || gameManager.isGameLost()) return;
            isHitThisTime = false;
            handler.postDelayed(this, gameManager.getDelay());
            gameManager.makeProgress();
            if ( gameManager.hasSpawn() )
                gameManager.spawnAsteroid();
            updateScreen();
            checkForHit();
        }
    };
    private String username;
    private Thread gameThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();

        gameManager = new GameManager(getIntent().getExtras().getBoolean("isLevelEasy"));
        updateScreen();
        this.username = getIntent().getExtras().getString("username");
        this.gameThread = new Thread(gameRunnable);
        gameThread.run();
        updateScreen();
    }



    private void checkForHit() {
        if ( gameManager.isHit()){
            SoundManager.getInstance().makeSoundNotInLoop(this, R.raw.crash);
        }
        if ( !this.isHitThisTime && gameManager.isHit()){
            gameManager.hitAsteroid();
            // also - remove 1 engine
            for( int i = 2; i >= gameManager.getLives(); i -- ){
                gameEngines[i].setVisibility(gameEngines[i].INVISIBLE);
            }
            vibrate();
            toast("WE GOT HIT");
            isHitThisTime = true;


            if  (this.gameManager.isGameLost()){
                this.gameThread.interrupt();
                try {
                    this.gameThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.gameManager.setPaused(false);

                Player player = new Player(
                        this.username,
                        this.gameManager.getScore(),
                        LocationManager.getInstance().getLongitude(),
                        LocationManager.getInstance().getLatitude()
                );
                if ( gameManager.isInHighScore(player)){
                    this.gameManager.addToHighScore(player);
                }
                String gameManagerJson = GsonManager.getInstance().toGson(this.gameManager);
                GsonManager.getInstance().putString(SP_KEY_GAME_MANAGER, gameManagerJson);

                callScoreIntent();
            }
        }
    }

    private void toast(String text) {
        Toast
                .makeText(this, text, Toast.LENGTH_LONG)
                .show();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrator.vibrate(500);
        }
    }

    private void findViews() {
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        rightBtn = (ImageView) findViewById(R.id.rightBtn);
        spaceship = (ImageView) findViewById(R.id.spaceship);
        spaceships = new ImageView[] {
                (ImageView) findViewById(R.id.spaceshipLeftLeft),
                (ImageView) findViewById(R.id.spaceshipLeft),
                spaceship,
                (ImageView) findViewById(R.id.spaceshipRight),
                (ImageView) findViewById(R.id.spaceshipRightRight)
        };

        asteroids = new ImageView[][]{
                {(ImageView) findViewById(R.id.Asteroid11),(ImageView) findViewById(R.id.Asteroid12),(ImageView) findViewById(R.id.Asteroid13),(ImageView) findViewById(R.id.Asteroid14),(ImageView) findViewById(R.id.Asteroid15) },
                {(ImageView) findViewById(R.id.Asteroid21),(ImageView) findViewById(R.id.Asteroid22),(ImageView) findViewById(R.id.Asteroid23),(ImageView) findViewById(R.id.Asteroid24),(ImageView) findViewById(R.id.Asteroid25) },
                {(ImageView) findViewById(R.id.Asteroid31),(ImageView) findViewById(R.id.Asteroid32),(ImageView) findViewById(R.id.Asteroid33),(ImageView) findViewById(R.id.Asteroid34),(ImageView) findViewById(R.id.Asteroid35) },
                {(ImageView) findViewById(R.id.Asteroid41),(ImageView) findViewById(R.id.Asteroid42),(ImageView) findViewById(R.id.Asteroid43),(ImageView) findViewById(R.id.Asteroid44),(ImageView) findViewById(R.id.Asteroid45) },
                {(ImageView) findViewById(R.id.Asteroid51),(ImageView) findViewById(R.id.Asteroid52),(ImageView) findViewById(R.id.Asteroid53),(ImageView) findViewById(R.id.Asteroid54),(ImageView) findViewById(R.id.Asteroid55) },
                {(ImageView) findViewById(R.id.Asteroid61),(ImageView) findViewById(R.id.Asteroid62),(ImageView) findViewById(R.id.Asteroid63),(ImageView) findViewById(R.id.Asteroid64),(ImageView) findViewById(R.id.Asteroid65) },
                {(ImageView) findViewById(R.id.Asteroid71),(ImageView) findViewById(R.id.Asteroid72),(ImageView) findViewById(R.id.Asteroid73),(ImageView) findViewById(R.id.Asteroid74),(ImageView) findViewById(R.id.Asteroid75) },
                {(ImageView) findViewById(R.id.Asteroid81),(ImageView) findViewById(R.id.Asteroid82),(ImageView) findViewById(R.id.Asteroid83),(ImageView) findViewById(R.id.Asteroid84),(ImageView) findViewById(R.id.Asteroid85) },
                {(ImageView) findViewById(R.id.Asteroid91),(ImageView) findViewById(R.id.Asteroid92),(ImageView) findViewById(R.id.Asteroid93),(ImageView) findViewById(R.id.Asteroid94),(ImageView) findViewById(R.id.Asteroid95) },
                {(ImageView) findViewById(R.id.Asteroid101),(ImageView) findViewById(R.id.Asteroid102),(ImageView) findViewById(R.id.Asteroid103),(ImageView) findViewById(R.id.Asteroid104),(ImageView) findViewById(R.id.Asteroid105) },
                {(ImageView) findViewById(R.id.Asteroid111),(ImageView) findViewById(R.id.Asteroid112),(ImageView) findViewById(R.id.Asteroid113),(ImageView) findViewById(R.id.Asteroid114),(ImageView) findViewById(R.id.Asteroid115) },
        };

        gameEngines = new ImageView[]{
                (ImageView) findViewById(R.id.gameImgEngine1),
                (ImageView) findViewById(R.id.gameImgEngine2),
                (ImageView) findViewById(R.id.gameImgEngine3),
        };
    }

    private void moveSpaceShip(int toward){
        gameManager.changeLocation(toward);

        // I would like to have the movement by transitioning
        // this.spaceshipLeft = gameManager.getLocation() * 350;
        // spaceships[1].setTranslationX(this.spaceshipLeft);
        // but since we have not learned it then

        int index = gameManager.getLocation() + 2;
        for(int i = 0 ; i < gameManager.getMat()[0].length; i ++ ){
            if (i == index)
                this.spaceships[i].setVisibility(View.VISIBLE);
            else
                this.spaceships[i].setVisibility(View.INVISIBLE);
        }

        checkForHit();
    }
    private void initViews() {
        this.leftBtn.setOnClickListener( v -> {
            this.moveSpaceShip(-1);

        });
        this.rightBtn.setOnClickListener( v -> {
            this.moveSpaceShip(1);
        });
    }

    private void updateScreen(){
//        this.gameManager.printMat();
        // update mat
        int[][] mat = gameManager.getMat();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                ImageView asteroid = this.asteroids[i][j];
                boolean isVisible = asteroid.getVisibility() == asteroid.VISIBLE;
                boolean isVisibleOnMat = mat[i][j] == 1;
                if ( !isVisibleOnMat && isVisible || isVisibleOnMat && !isVisible){
                    if ( !isVisible ) asteroid.setVisibility(asteroid.VISIBLE);
                    else if ( isVisible ) asteroid.setVisibility(asteroid.INVISIBLE);
                }
            }
        }
    }
    private void callScoreIntent() {
        Intent intent = new Intent(this, ScoreActivity.class);

        setIntentExtras(intent);

        startActivity(intent);
        finish();
    }

    private void setIntentExtras(Intent intent){
        intent.putExtra("username", this.username);

        for(int i = 0 ; i < 5 ; i ++ ){
            intent.putExtra(String.format("Player%d", i+1), GsonManager.getInstance().toGson(this.gameManager.gethighScore().get(i)));
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        // pause thread
        if ( !this.gameManager.isGameLost()){ // checks the cause of stopping - end game / pause
            this.gameManager.setPaused(true);
        }

        String gameManagerJson = GsonManager.getInstance().toGson(this.gameManager);
        GsonManager.getInstance().putString(SP_KEY_GAME_MANAGER, gameManagerJson);
        this.gameManager = null;
        this.gameThread.interrupt();
        try {
            this.gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.gameThread = null;
        SoundManager.getInstance().stopMainSound();

    }
    @Override
    public void onResume(){
        super.onResume();
        // resume thread
        // reset

//        String gameManagerJson = GsonManager.getInstance().toGson(this.gameManager);
//        GsonManager.getInstance().putString(SP_KEY_GAME_MANAGER, "");
        String gameManagerString = GsonManager.getInstance().getString(SP_KEY_GAME_MANAGER, "");
        if ( gameManagerString.equals("")) return;
        GameManager tempGM = GsonManager.getInstance().fromJson(gameManagerString, GameManager.class);

        if (!tempGM.isPaused())return;
        this.gameManager = tempGM;
        if ( this.gameThread == null ){
            this.gameThread = new Thread(this.gameRunnable);
            this.gameThread.run();
            SoundManager.getInstance().startMainSound(this, R.raw.main);
        }
        Log.d("GameManager: ", this.gameManager.toString());

        int index = gameManager.getLocation() + 2;
        for(int i = 0 ; i < gameManager.getMat()[0].length; i ++ ){
            if (i == index)
                this.spaceships[i].setVisibility(View.VISIBLE);
            else
                this.spaceships[i].setVisibility(View.INVISIBLE);
        }

        updateScreen();
    }
}