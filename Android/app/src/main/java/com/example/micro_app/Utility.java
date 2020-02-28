package com.example.micro_app;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Utility
{
    public static void ToastMessage(AppCompatActivity _activity, String message){
        Toast toast=Toast.makeText(_activity.getApplicationContext(),message,Toast.LENGTH_SHORT);

        toast.show();
    }
}
