package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Scan2roomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan2room);
        String room = getIntent().getExtras().getString("room");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://contact-api-dev-3sujih4x4a-uc.a.run.app/room?room_id=" + room;
        StringRequest studentRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {

                        //parse the response
                        JSONObject coor = new JSONObject(response);
                        String arr = coor.getString("aspect_ratio");
                        String arr2[] = arr.split("\"",5);
                        //Log.e("x ",arr2[1]);
                        //Log.e("y ",arr2[3]);
                        Intent getIntent = new Intent(getApplicationContext(),RoomActivity.class);
                        getIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        getIntent.putExtra("x",arr2[1]);
                        getIntent.putExtra("y",arr2[3]);
                        startActivity(getIntent);



                    } catch (JSONException e) {
                        Toast.makeText(Scan2roomActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }, error -> Toast.makeText(Scan2roomActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
        queue.add(studentRequest);
    }
}