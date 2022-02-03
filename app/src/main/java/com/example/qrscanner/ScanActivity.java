package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

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


public class ScanActivity extends AppCompatActivity {


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
        Log.e("DEBUG", "IS personal? " + personal);
        if (personal) {
            scanned_id = value.split("://")[1];
        } else {
            try {
                String[] arr = value.split("=", 2);
                scanned_id = arr[1];
            } catch (Exception e) {
                value = "No QR scanned or invalid QR code";
                finish();
            }
            if (scanned_id.equalsIgnoreCase("")) {
                finish();
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject bodyObject = new JSONObject();

        try {
            Log.e("adas",scanned_id);
            JSONObject scanObject = new JSONObject().put("type", (personal ? "PERSONAL" : "ROOM")).put("email", activeEmail).put("scanned_id", scanned_id);
            bodyObject.put("scan", scanObject);
            Log.e("DEBUG", "Json String: " + bodyObject.toString());
            Log.e("DEBUG", "Json String: " + scanObject.toString());
            Log.e("DEBUG", activeEmail);
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
        }
        StringRequest request = new JSONRequest(Request.Method.POST, "https://contact-api-dev-3sujih4x4a-uc.a.run.app/record_data", bodyObject, new com.android.volley.Response.Listener<String>() {
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
}
