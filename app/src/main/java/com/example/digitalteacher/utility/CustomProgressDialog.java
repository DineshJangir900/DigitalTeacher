package com.example.digitalteacher.utility;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog {
    private Context context;
    private ProgressDialog pDialog;

    public CustomProgressDialog(Context context){
        this.context = context;
        this.pDialog = new ProgressDialog(context);
    }

    public void  show(String title, String message){
        pDialog.setTitle(title);
        pDialog.setMessage(message);
        pDialog.show();
    }

    public boolean isShowing(){
        return pDialog.isShowing();
    }

    public void setProgress(int value){
        pDialog.setProgress(value);
    }

    public void setMessage(String msg){
        pDialog.setMessage(msg);
    }

    public void close(){
        pDialog.dismiss();
    }

    public void setCancelable(){
        pDialog.setCancelable(false);
    }
}
