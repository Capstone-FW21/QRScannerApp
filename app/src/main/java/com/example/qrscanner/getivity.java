package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class getivity extends AppCompatActivity {

    private TextView data_display;
    private String first_name;
    private String last_name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getivity);
        Bundle extras = getIntent().getExtras(); //get QR from main activities


        data_display = findViewById(R.id.text_view_result);
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://contact-api-dev-3sujih4x4a-uc.a.run.app/student";

// Request a string response from the provided URL.
        StringRequest studentRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject student = new JSONObject(response);
                        first_name = student.getString("first_name");
                        last_name = student.getString("last_name");
                        email = student.getString("email");
                        data_display.setText("Name: " + first_name + " " + last_name + "\nEmail: " + email );
                    } catch (JSONException e){
                        data_display.setText(e.getMessage());
                    }
                }, error -> data_display.setText("Error: " + error.getMessage()));

        queue.add(studentRequest);

    }
}