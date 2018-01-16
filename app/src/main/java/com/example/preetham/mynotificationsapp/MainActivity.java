package com.example.preetham.mynotificationsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //EditText edit = (EditText)findViewById(R.id.editText);
        String token = FirebaseInstanceId.getInstance().getToken();
        //edit.setText(token, TextView.BufferType.EDITABLE);
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                sendToServer(FirebaseInstanceId.getInstance().getToken());
            }
        });
        thread.start();
    }

    private void sendToServer(String token) {

        try {
            URL url = new URL("http://192.168.0.114:8080/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            //DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            //dos.writeBytes("token=" + token);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Do whatever you want after the
                // token is successfully stored on the server
                String json_response = "",text = "";
                EditText edit = (EditText)findViewById(R.id.editText);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((text = br.readLine()) != null) {
                    json_response += text;
                }
                //edit.setText(json_response, TextView.BufferType.EDITABLE);


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
