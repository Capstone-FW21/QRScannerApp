package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.Map;

public class HubActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.darklight == 1){
            setTheme(R.style.Dark);
            setContentView(R.layout.activity_setting);
        }
        else{
            setTheme(R.style.Light);
            setContentView(R.layout.activity_setting);
        }

        setContentView(R.layout.activity_hub);
        getSupportActionBar().setTitle("Covid-19 Tracker");

        File f = new File(
                "/data/data/com.example.qrscanner/shared_prefs/last_log.xml");
        if (f.exists() && MainActivity.persist_login) {
            //Log.e("HubPersistlog", String.valueOf(MainActivity.persist_login));
            SharedPreferences last_log = getSharedPreferences("last_log", MODE_PRIVATE);
            Map<String, ?> ll = last_log.getAll();
            if(!ll.isEmpty()) {
                for (Map.Entry<String, ?> entry : ll.entrySet()) {
                    String arr[] = entry.getKey().split("_", 3);
                    MainActivity.name = arr[0] + " " + arr[1];
                    MainActivity.activeId = Integer.parseInt(arr[2]);
                    MainActivity.activeEmail = entry.getValue().toString();
                    //Toast.makeText(HubActivity.this, MainActivity.activeEmail, Toast.LENGTH_LONG).show();
                    getSupportActionBar().setTitle(MainActivity.name);
                }
                View a = findViewById(R.id.textView);
                if(MainActivity.welcome) {
                    Snackbar.make(a, "Welcome back " + MainActivity.name, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    MainActivity.welcome = false;
                }
                Button logout = findViewById(R.id.logout);
                logout.setVisibility(View.VISIBLE);
            }
            else{
                //Toast.makeText(HubActivity.this, "no last login", Toast.LENGTH_LONG).show();
            }
        }



        //Account manager button on click
        Button btn_get = findViewById(R.id.account_btn);
        btn_get.setOnClickListener(v -> {
            StartActivityR(AccountActivity.class, 111);
        });

        //My QR code button on click
        Button btn_myqr = findViewById(R.id.myqr_btn);
        btn_myqr.setOnClickListener(v -> {

            if (MainActivity.activeEmail == null) {
                StartActivityR(AccountActivity.class, 111);
            } else {
                StartActivity(MyqrActivity.class);
            }

        });

        //Help button onclick
        Button btn_help = findViewById(R.id.help_btn);
        btn_help.setOnClickListener(v -> {
            StartActivity(HelpActivity.class);
        });

        //Setting button onclick
        Button btn_setting = findViewById(R.id.setting_btn);
        btn_setting.setOnClickListener(v -> {
            StartActivityR(SettingActivity.class,555);
        });

        Button btn_logout = findViewById(R.id.logout);
        btn_logout.setOnClickListener(v -> {
            btn_logout.setVisibility(View.GONE);
            MainActivity.activeEmail = null;
            MainActivity.activeId = 0;
            MainActivity.name = null;
            getSupportActionBar().setTitle("Covid-19 Tracker");
            View a = findViewById(R.id.textView);
            Snackbar.make(a, "You have logged-out, please login to start scanning", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SharedPreferences last_log = getSharedPreferences("last_log", MODE_PRIVATE);
            last_log.edit().clear().commit();
        });


        //Scan a qr code button on click
        FloatingActionButton camera_btn = findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(v -> {

            if(MainActivity.activeEmail == null){
                StartActivityR(AccountActivity.class, 111);
            }
            else {
                StartActivity(CameraActivity.class);
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
                MainActivity.name = name;
                Button logout = findViewById(R.id.logout);
                logout.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, activeEmail, Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == 555) {
                recreate();
            }
    }

    public void StartActivity(Class<?> the_activity){
        Intent getIntent = new Intent(getApplicationContext(),the_activity);
        getIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(getIntent);
    }

    public void StartActivityR(Class<?> the_activity, int code){
        Intent getIntent = new Intent(getApplicationContext(),the_activity);
        getIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(getIntent, code);
    }




}


