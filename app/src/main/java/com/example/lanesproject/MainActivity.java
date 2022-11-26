package com.example.lanesproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private GameManager gameManager;
    private int spaceshipLeft =0;
    boolean isHitThisTime = false;

    private ImageView[][] asteroids;
    private ImageView leftBtn;
    private ImageView rightBtn;
    private ImageView spaceship;
    private ImageView[] gameEngines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        gameManager = new GameManager();
        updateScreen();

        final Handler handler = new Handler(Looper.myLooper());
        Thread gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                isHitThisTime = false;
                handler.postDelayed(this, gameManager.getDelay());
                gameManager.makeProgress();
                if ( gameManager.hasSpawn() )
                    gameManager.spawnAsteroid();
                updateScreen();
                checkForHit();
            }
        });
        gameThread.run();

        updateScreen();
    }
    private void checkForHit() {
        if ( !this.isHitThisTime && gameManager.isHit()){
            gameManager.hitAsteroid();
            // also - remove 1 engine
            for( int i = 2; i >= gameManager.getLives(); i -- ){
                gameEngines[i].setVisibility(gameEngines[i].INVISIBLE);
            }
            vibrate();
            toast("WE GOT HIT");
            isHitThisTime = true;
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

        asteroids = new ImageView[][]{
                {(ImageView) findViewById(R.id.Asteroid11),(ImageView) findViewById(R.id.Asteroid12),(ImageView) findViewById(R.id.Asteroid13) },
                {(ImageView) findViewById(R.id.Asteroid21),(ImageView) findViewById(R.id.Asteroid22),(ImageView) findViewById(R.id.Asteroid23) },
                {(ImageView) findViewById(R.id.Asteroid31),(ImageView) findViewById(R.id.Asteroid32),(ImageView) findViewById(R.id.Asteroid33) },
                {(ImageView) findViewById(R.id.Asteroid41),(ImageView) findViewById(R.id.Asteroid42),(ImageView) findViewById(R.id.Asteroid43) },
                {(ImageView) findViewById(R.id.Asteroid51),(ImageView) findViewById(R.id.Asteroid52),(ImageView) findViewById(R.id.Asteroid53) },
                {(ImageView) findViewById(R.id.Asteroid61),(ImageView) findViewById(R.id.Asteroid62),(ImageView) findViewById(R.id.Asteroid63) },
                {(ImageView) findViewById(R.id.Asteroid71),(ImageView) findViewById(R.id.Asteroid72),(ImageView) findViewById(R.id.Asteroid73) },
        };

        gameEngines = new ImageView[]{
                (ImageView) findViewById(R.id.gameImgEngine1),
                (ImageView) findViewById(R.id.gameImgEngine2),
                (ImageView) findViewById(R.id.gameImgEngine3),
        };
    }
    private void moveSpaceShip(int toward){
        gameManager.changeLocation(toward);
        this.spaceshipLeft = gameManager.getLocation() * 350;
        spaceship.setTranslationX(this.spaceshipLeft);

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
        int[][] mat = gameManager.getMat();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
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
}