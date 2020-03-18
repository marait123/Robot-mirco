package com.example.micro_app;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);

        //Toast toast=Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT);
        //toast.setMargin(50,50);
        //toast.show();



        Utility.ToastMessage(this, "hi micro");
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech In", Toast.LENGTH_SHORT).show();
            //Utility.ToastMessage(this, "putYour Device Don't Support Speech In");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String talk = result.get(0);
                    // embed talk in json
                    // {
                    // status: "success",
                    // "message": "right"                    //
                    // }

                    // transmit the json if the talk has valid message
                    // else prompt the user to talk again

                    txvResult.setText(talk);
                    if(talk.trim().equals("hello")){
                        Utility.ToastMessage(this, "hello my love");
                    }
                }
                break;
        }
    }
}
