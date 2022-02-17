package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static String activeEmail = null;
    public static int activeId = 0;
    public static String name = null;
    public static int wait_s = 500;
    public static boolean persist_login = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartActivity(HubActivity.class);
                //StartActivity(RoomActivity.class);
                finish();
            }
        }, wait_s);

    }



    public void StartActivity(Class<?> the_activity){
        Intent getIntent = new Intent(getApplicationContext(),the_activity);
        getIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(getIntent);
    }

}