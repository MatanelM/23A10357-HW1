package com.example.lanesproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.lanesproject.R;
import com.example.lanesproject.managers.GsonManager;
import com.example.lanesproject.managers.LocationManager;
import com.example.lanesproject.managers.SoundManager;

public class MenuActivity extends AppCompatActivity {


    private Button easyButton;
    private Button difficultButton;
    private Button sensorModeButton;

    private boolean isLevelEasy = true;
    private boolean isSensorMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        initViews();
        initManagers();
        SoundManager.getInstance().startMainSound(this, R.raw.main);
    }
    private void initManagers() {
        SoundManager.init();
        LocationManager.init(this);
        GsonManager.init(this);
    }

    private void initViews() {
        easyButton.setOnClickListener( v -> {
            isLevelEasy = true;
            callMainIntent();
        });
        difficultButton.setOnClickListener( v -> {
            isLevelEasy = false;
            callMainIntent();
        });
        sensorModeButton.setOnClickListener( v -> {
            String text = (String) sensorModeButton.getText();
            if (text.contains("On")){
                sensorModeButton.setText("Sensor Mode: Off");
                isSensorMode = false;
            }else{
                sensorModeButton.setText("Sensor Mode: On");
                isSensorMode = true;
            }
        });
    }

    private void findViews() {
        easyButton = (Button) findViewById(R.id.easyButton);
        difficultButton = (Button) findViewById(R.id.difficultButton);
        sensorModeButton = (Button) findViewById(R.id.sensorModeButton);
    }

    private void callMainIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        setIntentExtras(intent);
        startActivity(intent);
        finish();
    }

    private void setIntentExtras(Intent intent){
        intent.putExtra("isLevelEasy", this.isLevelEasy);
        intent.putExtra("isSensorMode", this.isSensorMode);
        intent.putExtra("username", String.valueOf(((EditText)findViewById(R.id.Username)).getText()));
    }

}