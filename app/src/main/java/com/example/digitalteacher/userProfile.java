package com.example.digitalteacher;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userProfile extends AppCompatActivity {
    TextView textView,textView1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setTitle("Profile");

        ImageView imageView = (ImageView)findViewById(R.id.userImage);
        textView = (TextView)findViewById(R.id.userName);
        textView1 = (TextView)findViewById(R.id.userEmail);

        SharedPreferences result = getSharedPreferences("email", Context.MODE_PRIVATE);
        String email = result.getString( "email","Data not found");
        String name = result.getString( "name","Data not found");

        textView.setText(name);
        textView1.setText(email);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Glide.with(getApplicationContext())
                .load(user.getPhotoUrl().toString())
                .fitCenter()
                .override(100, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(userProfile.this,HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
