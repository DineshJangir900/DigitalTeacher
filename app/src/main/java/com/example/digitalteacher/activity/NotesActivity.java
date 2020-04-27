package com.example.digitalteacher.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.digitalteacher.adapter.DsaAdapter;
import com.example.digitalteacher.adapter.NotesAdapter;
import com.example.digitalteacher.application.DigitalTeacherApplication;
import com.example.digitalteacher.databinding.ActivityNotesBinding;
import com.example.digitalteacher.model.CppModelClass;
import com.example.digitalteacher.model.DsaModelClass;
import com.example.digitalteacher.model.NotesModelClass;
import com.example.digitalteacher.utility.CustomProgressDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {
    private ActivityNotesBinding binding;

    private DatabaseReference demoRef;
    private CustomProgressDialog progressDialog;
    private String name;

    private List<NotesModelClass> arrayList;
    private String currentDate;
    private NotesAdapter notesAdapter;
    private boolean checkBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Personal Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        binding.currentDate.setText("Date: " + currentDate);

        binding.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.noteHandingET.getText().toString()) || TextUtils.isEmpty(binding.noteEt.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please fill the all values..", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }

            }
        });

        binding.addContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addContentBtn.setVisibility(View.GONE);
                binding.linearLayout.setVisibility(View.VISIBLE);
                checkBack = true;
            }
        });

        SharedPreferences result = getSharedPreferences("email", Context.MODE_PRIVATE);
        name = result.getString("name", "Data not found");

        binding.dsaRecyclerView.setHasFixedSize(true);
        binding.dsaRecyclerView.setLayoutManager(new LinearLayoutManager(NotesActivity.this));

        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCancelable();
        progressDialog.show("Loading Data...", "please wait...");


        arrayList = new ArrayList<>();

        demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("notesUploads");
        demoRef.orderByChild("userEmail").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NotesModelClass notesModelClass = new NotesModelClass();
                    notesModelClass = postSnapshot.getValue(NotesModelClass.class);
                    arrayList.add(notesModelClass);
                }
                progressDialog.close();
                Collections.reverse(arrayList);
                notesAdapter = new NotesAdapter(binding.dsaRecyclerView, getApplicationContext(), arrayList);
                binding.dsaRecyclerView.setAdapter(notesAdapter);
                notesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                arrayList = new ArrayList<>();
                demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("notesUploads");
                demoRef.orderByChild("userEmail").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            NotesModelClass notesModelClass = new NotesModelClass();
                            notesModelClass = postSnapshot.getValue(NotesModelClass.class);
                            arrayList.add(notesModelClass);
                        }

                        Collections.reverse(arrayList);
                        notesAdapter = new NotesAdapter(binding.dsaRecyclerView, getApplicationContext(), arrayList);
                        binding.dsaRecyclerView.setAdapter(notesAdapter);
                        notesAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                binding.swipeRefresh.setRefreshing(false);
            }
        });

    }


    private void uploadFile() {
        progressDialog = new CustomProgressDialog(this);
        progressDialog.show("Uploading file...","please wait");

        demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("notesUploads");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NotesModelClass dsa = new NotesModelClass();
                dsa.setUserEmail(name);
                dsa.setDate(currentDate);
                dsa.setNoteHanding(binding.noteHandingET.getText().toString().toUpperCase());
                dsa.setNote(binding.noteEt.getText().toString());
                demoRef.push().setValue(dsa);

                binding.noteHandingET.setText("");
                binding.noteEt.setText("");
                binding.linearLayout.setVisibility(View.GONE);
                binding.addContentBtn.setVisibility(View.VISIBLE);
                progressDialog.close();
                checkBack = false;
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Note successfully uploaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Note not uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(checkBack == true){
            binding.linearLayout.setVisibility(View.GONE);
            binding.addContentBtn.setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }
    }
}