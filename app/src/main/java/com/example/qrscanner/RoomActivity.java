package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qrscanner.requests.JSONRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomActivity extends AppCompatActivity implements View.OnTouchListener {

    private String x="20";
    private String y="20";
    private String roomURL = ScanActivity.url;
    float dX, dY;
    float dXsubmit = 0, dYsubmit = 0;
    Boolean touch = false;
    int xint = 0;
    int yint = 0;
    int scalingD = 0;
    int scalingU = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.darklight == 1){
            setTheme(R.style.Dark);
            setContentView(R.layout.activity_room);
        }
        else{
            setTheme(R.style.Light);
            setContentView(R.layout.activity_room);
        }
        getSupportActionBar().hide();
        FrameLayout fl = findViewById(R.id.fr);

        x = getIntent().getExtras().getString("x");
        y = getIntent().getExtras().getString("y");

        Log.e("x",x);
        Log.e("y",y);

        xint = Integer.parseInt(x);
        yint = Integer.parseInt(y);

        if(xint > 300 || yint > 300) {
            while (xint > 300 || yint > 300) {
                xint = xint / 2;
                yint = yint / 2;
                scalingD++;
            }
        }
        if(xint < 30 || yint < 30){
            while (xint < 30 || yint < 30) {
                xint = xint * 2;
                yint = yint * 2;
                scalingU++;
            }
        }

        drawRoom dr = new drawRoom(this,xint,yint);
        dr.setOnTouchListener(this);


        fl.addView(dr);

        Button btn_here = findViewById(R.id.submit_btn);
        btn_here.setOnClickListener(v -> {
            if(touch) {
                submit_room_with_coordinate();
                finish();
            }
            else{
                View a = findViewById(R.id.x_cor);
                Snackbar.make(a, "Select approx location in the room (tab square)" + MainActivity.name, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {


        dX = ((view.getX() - event.getRawX())*-1)/6;
        dY = ((view.getY() - event.getY())*-1)/6;

        int roomSizeX = xint;
        int roomSizeY = yint;

        int boundx = (Resources.getSystem().getDisplayMetrics().widthPixels)/6; //1080 test subject
        //int boundy = (Resources.getSystem().getDisplayMetrics().heightPixels)/6; //2040 text subject
        int boundy = (findViewById(R.id.fr).getHeight())/6;

        dX -= ((boundx - roomSizeX) / 2);
        dY -= ((boundy - roomSizeY) / 2);

        Log.e("ySize",String.valueOf(boundy));

        if(dX >= 0 && dX <= roomSizeX && dY >= 0 && dY <= roomSizeY){
            int fdX = (int) dX;

            if(scalingD != 0) {
                fdX = fdX * (scalingD * 2);
            }

            if(scalingU != 0) {
                for(int i = 0; i<scalingU;i++){
                    fdX = fdX / 2;
                }
            }

            String xx = String.valueOf(fdX);
            dXsubmit = dX;
            TextView xc = findViewById(R.id.x_cor);
            xc.setText(xx);

            int fdY = (int) dY;

            if(scalingD != 0) {
                fdY = fdY * (scalingD * 2);
            }

            if(scalingU != 0) {
                for(int i = 0; i<scalingU;i++){
                    fdY = fdY / 2;
                }
            }

            String yy = String.valueOf(fdY);
            Log.e("yT",yy);
            dYsubmit = dY;
            TextView yc = findViewById(R.id.y_cor);
            yc.setText(yy);

            touch = true;

        }

        return true;
    }

    public void submit_room_with_coordinate(){
        roomURL += "?xcoord="+ dXsubmit /xint + "&ycoord=" + dYsubmit / yint ;
        Log.e("roomURL" ,roomURL);
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject bodyObject = new JSONObject();

        try {

            JSONObject scanObject = new JSONObject().put("type", "ROOM").put("email", getIntent().getExtras().getString("email")).put("scanned_id", getIntent().getExtras().getString("room"));
            bodyObject.put("scan", scanObject);
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
        }


        StringRequest request = new JSONRequest(Request.Method.POST, roomURL, bodyObject, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("RESPONSE", response);
                Toast toast = Toast.makeText(RoomActivity.this, "Successfully submitted!", Toast.LENGTH_LONG);
                toast.show();
                getSupportActionBar().setTitle("Room tracking Added");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //Log.e("RESPONSE", error.getMessage());
                Toast toast = Toast.makeText(RoomActivity.this, "Error Submitting!\n" + error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        queue.add(request);
        queue.start();
        finish();
    }

}