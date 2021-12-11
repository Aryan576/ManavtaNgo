package com.manavta.ngoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.manavta.ngoapp.ngo.NGOHomeActivity;

public class MainSplashScreen extends AppCompatActivity {
    int time = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        String id = sharedPreferences.getString("roleid", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (email.equals("")) {
                    Intent i = new Intent(MainSplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();


                } else {

                    if (id.equals("2")) {

                        Intent i = new Intent(MainSplashScreen.this, NGOHomeActivity.class);
                        startActivity(i);
                        finish();

                    } else if (id.equals("1")) {


                        Intent i = new Intent(MainSplashScreen.this, HomeDashActivity.class);
                        startActivity(i);
                        finish();

                    }

                }


            }
        }, time);
    }
}