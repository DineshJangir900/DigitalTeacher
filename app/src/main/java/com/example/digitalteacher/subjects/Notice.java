package com.example.digitalteacher.subjects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.digitalteacher.R;

public class Notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        this.setTitle("Notice Board");

    }
}
