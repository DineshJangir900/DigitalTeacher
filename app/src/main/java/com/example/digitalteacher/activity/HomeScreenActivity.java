package com.example.digitalteacher.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.digitalteacher.R;
import com.example.digitalteacher.activity.ui.aboutUs.AboutUsFragment;
import com.example.digitalteacher.activity.ui.profile.ProfileFragment;
import com.example.digitalteacher.activity.ui.home.HomeFragment;
import com.example.digitalteacher.databinding.ActivityHomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class HomeScreenActivity extends AppCompatActivity {
    private ActivityHomeScreenBinding homeScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(homeScreenBinding.getRoot());
        setTitle("Digital Teacher");
        homeScreenBinding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transactionManager = getSupportFragmentManager().beginTransaction();
        transactionManager.replace(R.id.nav_host_fragment, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    break;
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    break;
                case R.id.navigation_aboutUs:
                    fragment = new AboutUsFragment();
                    loadFragment(fragment);
                    break;

            }
            return true;
        }
    };

}
