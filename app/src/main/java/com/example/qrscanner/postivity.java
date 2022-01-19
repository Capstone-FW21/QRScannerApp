package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qrscanner.requests.JSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.logging.Logger;



public class postivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postivity);
        
        Bundle extras = getIntent().getExtras(); //get QR from main activities
        String value = "";
        String email = "";
        String room = "";


        if (extras == null) {
            finish();
            return;
        }
        value = extras.getString("key");
        Log.e("email111", extras.getString("email"));
        email = extras.getString("email");
        try {
            String[] arr = value.split("=", 2);
            room = arr[1];
        } catch (Exception e) {
            value = "No QR scanned or invalid QR code";
            finish();
        }

        if (room.equalsIgnoreCase("")) {
            finish();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject bodyObject = new JSONObject();

        try {
            bodyObject.put("scan", new JSONObject().put("email", email).put("room_id", room));
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
        }
        StringRequest request = new JSONRequest(Request.Method.POST, "https://contact-api-dev-3sujih4x4a-uc.a.run.app/record_data", bodyObject, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("RESPONSE", response);
                Toast toast = Toast.makeText(postivity.this, "Successfully submitted!", Toast.LENGTH_LONG);
                toast.show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //Log.e("RESPONSE", error.getMessage());
                Toast toast = Toast.makeText(postivity.this, "Error Submitting!\n" + error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        queue.add(request);
        queue.start();
        finish();
    }
}
