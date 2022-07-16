package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.example.study_app.R;

public class Splash_Screen extends AppCompatActivity {
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        preferencesManager=new PreferencesManager(Splash_Screen.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferencesManager.getUserId().equalsIgnoreCase("")){
                    startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        },3000);
    }
}