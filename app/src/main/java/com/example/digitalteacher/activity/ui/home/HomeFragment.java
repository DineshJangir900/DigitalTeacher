package com.example.digitalteacher.activity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.digitalteacher.R;
import com.example.digitalteacher.activity.CppActivity;
import com.example.digitalteacher.activity.DsaActivity;
import com.example.digitalteacher.activity.HomeScreenActivity;
import com.example.digitalteacher.activity.NotesActivity;
import com.example.digitalteacher.activity.OsActivity;
import com.example.digitalteacher.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = homeBinding.getRoot();

        homeBinding.dsaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), DsaActivity.class));
            }
        });

        homeBinding.cppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CppActivity.class));
            }
        });

        homeBinding.osBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), OsActivity.class));
            }
        });

        homeBinding.notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotesActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
