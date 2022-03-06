package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.darklight == 1){
            setTheme(R.style.Dark);
            setContentView(R.layout.activity_help);
        }
        else{
            setTheme(R.style.Light);
            setContentView(R.layout.activity_help);
        }

        getSupportActionBar().setTitle("Help");
        TextView help = findViewById(R.id.helptext);
        help.setText("Guide on how to use the app / link to website with guide");
    }
}