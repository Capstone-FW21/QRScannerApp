package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RoomActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        FrameLayout fl = findViewById(R.id.fr);
        drawRoom dr = new drawRoom(this,150,100);
        dr.setOnTouchListener(this);
        fl.addView(dr);


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

    public void get_room_size(){
        String room = getIntent().getExtras().getString("room");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://contact-api-dev-3sujih4x4a-uc.a.run.app/room?room_id=" + room;
    }

}