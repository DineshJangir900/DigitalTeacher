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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.digitalteacher.adapter.CppAdapter;
import com.example.digitalteacher.application.DigitalTeacherApplication;
import com.example.digitalteacher.databinding.ActivityCppBinding;
import com.example.digitalteacher.model.CppModelClass;
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

public class CppActivity extends AppCompatActivity {
    private ActivityCppBinding binding;
    private Uri selectData;
    private StorageReference storageReference;
    private DatabaseReference demoRef;
    private CustomProgressDialog progressDialog;
    private String email;
    private String url,fileName1;
    private List<CppModelClass> arrayList;
    private String currentDate;
    private CppAdapter cppAdapter;
    private boolean checkBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("C++");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        binding.currentDate.setText("Date: "+currentDate);

        binding.selectFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();
            }
        });
        binding.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.topicNameEt.getText().toString()) || TextUtils.isEmpty(binding.fileTypeEt.getText().toString()) ) {
                    Toast.makeText(getApplicationContext(),"Please fill the all values..",Toast.LENGTH_SHORT).show();
                }else{
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
        email = result.getString("email", "Data not found");

        binding.dsaRecyclerView.setHasFixedSize(true);
        binding.dsaRecyclerView.setLayoutManager(new LinearLayoutManager(CppActivity.this));

        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCancelable();
        progressDialog.show("Loading Data...","please wait...");

        arrayList = new ArrayList<>();

        demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("cppUploads");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CppModelClass cppModelClass = new CppModelClass();
                    cppModelClass = postSnapshot.getValue(CppModelClass.class);
                    arrayList.add(cppModelClass);
                }
                progressDialog.close();
                Collections.reverse(arrayList);
                cppAdapter = new CppAdapter(CppActivity.this,binding.dsaRecyclerView, getApplicationContext() ,arrayList);
                binding.dsaRecyclerView.setAdapter(cppAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firebaseRetrive();
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        binding.searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    search(editable.toString());
                }else{
                    search(" ");
                    firebaseRetrive();
                }
            }
        });

    }
    private void search(String s){
        arrayList.clear();
        arrayList = new ArrayList<>();

        demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("cppUploads");
        demoRef.orderByChild("topicName").equalTo(s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CppModelClass cppModelClass = new CppModelClass();
                    cppModelClass = postSnapshot.getValue(CppModelClass.class);
                    arrayList.add(cppModelClass);
                }
                progressDialog.close();
                Collections.reverse(arrayList);
                cppAdapter = new CppAdapter(CppActivity.this,binding.dsaRecyclerView, getApplicationContext() ,arrayList);
                binding.dsaRecyclerView.setAdapter(cppAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void firebaseRetrive(){
        arrayList.clear();
        arrayList = new ArrayList<>();

        demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("cppUploads");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CppModelClass cppModelClass = new CppModelClass();
                    cppModelClass = postSnapshot.getValue(CppModelClass.class);
                    arrayList.add(cppModelClass);
                }
                Collections.reverse(arrayList);
                cppAdapter = new CppAdapter(CppActivity.this,binding.dsaRecyclerView, getApplicationContext() ,arrayList);
                binding.dsaRecyclerView.setAdapter(cppAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 90);
    }

    private void uploadFile(){
        if(selectData == null){
            Toast.makeText(getApplicationContext(),"Nothing to upload",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog = new CustomProgressDialog(this);
            progressDialog.setProgress(0);
            progressDialog.show("Uploading file...","please wait");
            fileName1 = System.currentTimeMillis()+"";
            storageReference = DigitalTeacherApplication.getStorageReferenceInstance();
            storageReference.child("Uploads").child("cpp").child(fileName1).putFile(selectData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.child("Uploads").child("cpp").child(fileName1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    Log.d("ccc",""+downloadUrl);
                                    url = downloadUrl.toString();
                                    demoRef = DigitalTeacherApplication.getFirebaseDBInstance().child("cppUploads");
                                    demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            CppModelClass dsa = new CppModelClass();
                                            dsa.setFileName(fileName1);
                                            dsa.setFileUrl(url);
                                            dsa.setUserEmail(email);
                                            dsa.setDate(currentDate);
                                            dsa.setFileType(binding.fileTypeEt.getText().toString().toUpperCase());
                                            dsa.setTopicName(binding.topicNameEt.getText().toString());
                                            demoRef.push().setValue(dsa);
                                            progressDialog.close();
                                            binding.topicNameEt.setText("");
                                            binding.fileTypeEt.setText("");
                                            binding.linearLayout.setVisibility(View.GONE);
                                            binding.addContentBtn.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), "File successfully uploaded", Toast.LENGTH_LONG).show();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), "File not successfully uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "File not successfully uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"File not successfully uploaded",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgress);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 90 && resultCode == RESULT_OK && data != null) {
            selectData = data.getData();
            binding.selectFileTV.setText("A file is Selected :" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectFile();
        }
        else
            Toast.makeText(getApplicationContext(),"please provide permission",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(checkBack == true){
            binding.linearLayout.setVisibility(View.GONE);
            binding.addContentBtn.setVisibility(View.VISIBLE);
            checkBack = false;
        }else{
            super.onBackPressed();
        }
    }
}

