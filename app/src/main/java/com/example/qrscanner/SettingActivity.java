package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    Boolean save = false;

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

        Log.e("darklight", String.valueOf(MainActivity.darklight));
        setUI();
        getSupportActionBar().setTitle("Settings (WIP)");
        SharedPreferences accountList = getSharedPreferences("account_list", MODE_PRIVATE);


        Button rm_acc_btn = findViewById(R.id.rm_accounts);
        rm_acc_btn.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Confirm all account deletion");
            builder.setMessage("WIP, will not delete account yet");
            builder.setPositiveButton("Confirm", (dialog, which) -> {

                if(MainActivity.name == null) {
                    accountList.edit().clear().commit();
                    finish();
                }
                else{
                    accountList.edit().clear().commit();
                    String arr[] = MainActivity.name.split(" ",2);
                    SharedPreferences.Editor editor = accountList.edit();
                    editor.putString(arr[0] + "_" + arr[1] + "_" + MainActivity.activeId, MainActivity.activeEmail);
                    editor.commit();
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(SettingActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        Switch rmbr = findViewById(R.id.remember);
        rmbr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = cf.edit();
                    editor.remove("remember");
                    editor.putBoolean("remember",true);
                    editor.commit();
                }
                else{
                    SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = cf.edit();
                    editor.remove("remember");
                    editor.putBoolean("remember",false);
                    editor.commit();
                }

            }

        });

        Switch title = findViewById(R.id.titlepage);
        title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = cf.edit();
                    editor.remove("title");
                    editor.putBoolean("title",true);
                    editor.commit();
                }
                else{
                    SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = cf.edit();
                    editor.remove("title");
                    editor.putBoolean("title",false);
                    editor.commit();
                }

            }

        });

        Switch dark = findViewById(R.id.darklight);
        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = cf.edit();
                    editor.remove("darklight");
                    editor.putBoolean("darklight",true);
                    editor.commit();
                    MainActivity.darklight = 1;
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                else{
                    SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = cf.edit();
                    editor.remove("darklight");
                    editor.putBoolean("darklight",false);
                    editor.commit();
                    MainActivity.darklight = 0;
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

            }

        });

        if(MainActivity.darklight == 1) {
            rmbr.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            dark.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
        else{
            rmbr.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            dark.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        }

    }

    public void setUI(){
        SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
        Map<String, ?> sp = cf.getAll();

        for (Map.Entry<String, ?> entry : sp.entrySet()) {
            if(entry.getKey().compareTo("remember") == 0){
                save = Boolean.parseBoolean(entry.getValue().toString());
                Switch sw = findViewById(R.id.remember);
                sw.setChecked(save);
            }
            if(entry.getKey().compareTo("title") == 0){
                save = Boolean.parseBoolean(entry.getValue().toString());
                Switch sw = findViewById(R.id.titlepage);
                sw.setChecked(save);
            }
            if(entry.getKey().compareTo("darklight") == 0){
                save = Boolean.parseBoolean(entry.getValue().toString());
                Switch sw = findViewById(R.id.darklight);
                sw.setChecked(save);
            }
        }
    }
}