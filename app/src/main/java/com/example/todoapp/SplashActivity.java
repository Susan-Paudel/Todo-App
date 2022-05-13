package com.example.todoapp;
//import required Library

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

//create class SplashActivity that inherit property of AppCompatActivity
public class SplashActivity extends AppCompatActivity {
    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super keyword is used to refer parent class object
        super.onCreate(savedInstanceState);
        //it set the XML file as your main layout when the app starts
        setContentView(R.layout.activity_splash);
        //post time is set to 3000 milli second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //SharedPreferences store and retrieve small amounts of primitive data as key/value pairs
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
                //setting the authentication to false
                Boolean authentication = preferences.getBoolean("authentication", false);
                //if the user is authenticate
                if (authentication) {
                    //creating instance of intent that give path to redirect to another activity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    //start activity simply redirect to next page
                    startActivity(intent);
                    //finish the execution
                    finish();
                } else {
                    //creating instance of intent that give path to redirect to another activity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    //start activity simply redirect to next page
                    startActivity(intent);
                    //finish the execution
                    finish();
                }
            }
        }, 3000);
    }
}
