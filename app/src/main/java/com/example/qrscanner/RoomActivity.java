package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RoomActivity extends AppCompatActivity implements View.OnTouchListener {

    private String x="20";
    private String y="20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().hide();
        FrameLayout fl = findViewById(R.id.fr);

        x = getIntent().getExtras().getString("x");
        y = getIntent().getExtras().getString("y");

        Log.e("x",x);
        Log.e("y",y);

        drawRoom dr = new drawRoom(this,Integer.parseInt(x),Integer.parseInt(y));
        dr.setOnTouchListener(this);


        fl.addView(dr);

        Button btn_here = findViewById(R.id.submit_btn);
        btn_here.setOnClickListener(v -> {
            finish();
        });
    }

    float dX, dY;
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }
        return true;
    }

}