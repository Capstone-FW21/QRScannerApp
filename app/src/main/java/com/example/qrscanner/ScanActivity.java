package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qrscanner.requests.JSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class ScanActivity extends AppCompatActivity {

    public static int xcor = 0;
    public static int ycor = 0;
    public static String url = "https://contact-api-dev-3sujih4x4a-uc.a.run.app/record_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Bundle extras = getIntent().getExtras(); //get QR from main activities
        String value = "";
        String scanned_id = "";
        String activeEmail = "";
        if (extras == null) {
            finish();
            return;
        }
        activeEmail = extras.getString("activeEmail");
        value = extras.getString("key");
        boolean personal = value.startsWith("personal_scan://");
        //String url = "https://contact-api-dev-3sujih4x4a-uc.a.run.app/record_data";

        if (personal) {
            scanned_id = value.split("://")[1];
        } else {

            try {
                String[] arr = value.split("=", 2);
                scanned_id = arr[1];



                Intent getIntent = new Intent(getApplicationContext(),Scan2roomActivity.class);
                getIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getIntent.putExtra("room",scanned_id);
                getIntent.putExtra("email",activeEmail);
                startActivity(getIntent);
                //Log.e("tetst", String.valueOf(xcor));
                //to add, room coordinate selection
                // get room size
                //append url to include x,y
                //url += "?xcoord=" + random(50) + "&ycoord=" + random(50); //temporary coordinate data, its randomize between 0-50, room activity WIP

            } catch (Exception e) {
                value = "No QR scanned or invalid QR code";
                finish();
            }
            if (scanned_id.equalsIgnoreCase("")) {
                finish();
            }
        }

        if(personal) {
            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject bodyObject = new JSONObject();

            try {

                JSONObject scanObject = new JSONObject().put("type", (personal ? "PERSONAL" : "ROOM")).put("email", activeEmail).put("scanned_id", scanned_id);
                bodyObject.put("scan", scanObject);
            } catch (JSONException e) {
                e.printStackTrace();
                finish();
            }


            StringRequest request = new JSONRequest(Request.Method.POST, url, bodyObject, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v("RESPONSE", response);
                    Toast toast = Toast.makeText(ScanActivity.this, "Successfully submitted!", Toast.LENGTH_LONG);
                    toast.show();
                    getSupportActionBar().setTitle("Room tracking Added");
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //Log.e("RESPONSE", error.getMessage());
                    Toast toast = Toast.makeText(ScanActivity.this, "Error Submitting!\n" + error.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            });

            queue.add(request);
            queue.start();
            finish();
        }
        finish();

    }

    //to be deleted, temporary function for coordinate generation
    public int random(int max){
        final int random = new Random().nextInt((max - 1) + 1) + 1;
        return random;
    }
    //=============================================================



}


