package com.example.digitalteacher.activity.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.digitalteacher.R;
import com.example.digitalteacher.activity.GoogleSignInActicity;
import com.example.digitalteacher.databinding.FragmentProfileBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding profileBinding;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        View root = profileBinding.getRoot();

        SharedPreferences result = getContext().getSharedPreferences("email", Context.MODE_PRIVATE);
        String email = result.getString( "email","Data not found");
        String name = result.getString( "name","Data not found");

        profileBinding.userNameTV.setText(name);
        profileBinding.userEmailTV.setText(email);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Glide.with(getActivity().getApplicationContext())
                .load(user.getPhotoUrl().toString())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileBinding.userImage);

        profileBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), GoogleSignInActicity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
