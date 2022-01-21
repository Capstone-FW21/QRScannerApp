package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static String activeEmail = null;
    public String name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Cov-19 Tracker");

        //Account manager button on click
        Button btn_get = findViewById(R.id.account_btn);
        btn_get.setOnClickListener(v -> {

            Intent getIntent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivityForResult(getIntent, 111);

        });

        //My QR code button on click
        Button btn_myqr = findViewById(R.id.myqr_btn);
        btn_myqr.setOnClickListener(v -> {

            if (activeEmail == null) {

                Intent getIntent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivityForResult(getIntent, 111);

            } else {

                Intent getIntent = new Intent(getApplicationContext(), MyqrActivity.class);
                startActivity(getIntent);

            }

        });

        //Help button onclick
        Button btn_help = findViewById(R.id.help_btn);
        btn_help.setOnClickListener(v -> {



        });

        //Scan a qr code button on click
        FloatingActionButton camera_btn = findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(v -> {

            if(activeEmail == null){
                Intent getIntent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivityForResult(getIntent, 111);
            }
            else {
                Intent getIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(getIntent);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 111) {
            if (data.hasExtra("first")) {
                String name = data.getExtras().getString("first") + " " + data.getExtras().getString("last");
                getSupportActionBar().setTitle(name);
                this.name = name;
                //Toast.makeText(MainActivity.this, activeEmail, Toast.LENGTH_LONG).show();
            }
        }
    }



}