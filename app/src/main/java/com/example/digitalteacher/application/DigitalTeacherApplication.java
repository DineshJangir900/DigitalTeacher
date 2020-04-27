package com.example.digitalteacher.application;

import android.app.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DigitalTeacherApplication extends Application {

    private static DigitalTeacherApplication mInstance;
    private static StorageReference mStorageRef;
    private static DatabaseReference rootRef;
    private static FirebaseDatabase firebaseDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        mStorageRef = firebaseStorage.getReference();
    }

    public static synchronized DatabaseReference getFirebaseDBInstance(){
        return rootRef;
    }

    public static synchronized DigitalTeacherApplication getInstance() {
        return mInstance;
    }

    public static synchronized StorageReference getStorageReferenceInstance() { return mStorageRef; }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
