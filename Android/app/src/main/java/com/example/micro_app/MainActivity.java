package com.example.micro_app;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    //Thread Thread1 = null;
    private TextView txvResult;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //String SERVER_IP ;
    //int SERVER_PORT = 8080;
    string message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        //Variables To Get Connection IP and Port From User
        //etIP = findViewById(R.id.etIP);
        //etPort = findViewById(R.id.etPort);


        // hide the action bar
        getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Commented Code Gets Connection Data and Starts Connection through Thread1
                /*SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
                Thread1 = new Thread(new Thread1());
                Thread1.start();
                */
                promptSpeechInput();
            }
        });

        //Toast toast=Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT);
        //toast.setMargin(50,50);
        //toast.show();

        //Utility.ToastMessage(this, "hi micro");
    }

    public void promptSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
        /*if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "putYour Device Don't Support Speech In", Toast.LENGTH_SHORT).show();
            //Utility.ToastMessage(this, "putYour Device Don't Support Speech In");

        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String talk = result.get(0);

                    txvResult.setText(talk);
                    if(talk.trim().equals("hello")) {
                        Utility.ToastMessage(this, "hello my love");
                    }
                }
                break;
        }
    }
    /*
    private PrintWriter output;
   private BufferedReader input;
   class Thread1 implements Runnable {
      @Override
      public void run() {
         Socket socket;
         try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            output = new PrintWriter(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  tvMessages.setText("Connected\n");
               }
            });
            new Thread(new Thread2()).start();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   class Thread2 implements Runnable {
      @Override
      public void run() {
         while (true) {
            try {
               final String message = input.readLine();
               if (message ! = null) {
                  runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                        tvMessages.append("server: " + message + "\n");
                     }
                  });
               } else {
                  Thread1 = new Thread(new Thread1());
                  Thread1.start();
                  return;
               }
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }
   class Thread3 implements Runnable {
      private String message;
      Thread3(String message) {
         this.message = message;
      }
      @Override
      public void run() {
         output.write(message);
         output.flush();
         runOnUiThread(new Runnable() {
            @Override
            public void run() {
               tvMessages.append("client: " + message + "\n");
               etMessage.setText("");
            }
         });
      }
   }
     */
}
