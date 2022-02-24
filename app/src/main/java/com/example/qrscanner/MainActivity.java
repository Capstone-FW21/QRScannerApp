package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static String activeEmail = null;
    public static int activeId = 0;
    public static String name = null;
    public static int wait_s = 2000;
    public static boolean persist_login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckSetting();
        getSupportActionBar().hide();
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartActivity(HubActivity.class);
                finish();
            }
        }, wait_s);

    }



    public void StartActivity(Class<?> the_activity){
        Intent getIntent = new Intent(getApplicationContext(),the_activity);
        getIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(getIntent);
    }

    public void CheckSetting(){
        File f = new File(
                "/data/data/com.example.qrscanner/shared_prefs/config.xml");

        if(f.exists()){
            SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
            Map<String, ?> sp = cf.getAll();

            for (Map.Entry<String, ?> entry : sp.entrySet()) {
                if(entry.getKey().compareTo("remember") == 0){
                    if(entry.getValue().toString().compareTo("true") == 0)
                        persist_login = true;
                    else
                        persist_login = false;
                }
                if(entry.getKey().compareTo("title") == 0){
                    if(entry.getValue().toString().compareTo("true") == 0)
                        wait_s = 2000;
                    else
                        wait_s = 100;
                }
            }
        }
        else{
            SharedPreferences cf = getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = cf.edit();
            editor.remove("remember");
            editor.putBoolean("remember",false);
            editor.commit();
        }
    }

}