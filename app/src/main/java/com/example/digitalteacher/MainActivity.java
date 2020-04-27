package com.example.digitalteacher;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.digitalteacher.activity.GoogleSignInActicity;

public class MainActivity extends AppCompatActivity {
    private  static final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, GoogleSignInActicity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }

}
