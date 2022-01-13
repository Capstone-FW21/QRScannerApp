package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URL;
import java.util.logging.Logger;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class postivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postivity);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        Bundle extras = getIntent().getExtras(); //get QR from main activities
        String value ="";
        String email ="";
        String room ="";

        if (extras != null) {
            value = extras.getString("key");
            Log.e("email111",value);
            try {
                URL url = new URL(value);
                value = url.getQuery();
                String[] arr = value.split("&",2);
                email = arr[0];
                room = arr[1];

                arr = email.split("=",2);
                email = arr[1];
                arr = room.split("=",2);
                room = arr[1];


            } catch (Exception e) {
                value = "No QR scanned or invalid QR code";
            }

            if(email != "" && room != ""){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://contact-api-dev-3sujih4x4a-uc.a.run.app/")
                        .addConverterFactory(GsonConverterFactory.create()).client(client)
                        .build();
                Post_interface post_interface = retrofit.create(Post_interface.class);

                Post post = new Post(email,room);
                Log.e("email",post.getEmail());

                Call<String> call = post_interface.createPost(post.getEmail(),post.getRoom_id());


                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        TextView result_s = findViewById(R.id.url_show);
                        if(response.isSuccessful()){
                            result_s.setText("Data added with server response: " + response.code() + "\n\nThe following data has been added"
                            + "\n\nEmail: " + post.getEmail() + "\nroom: " + post.getRoom_id());
                            return;
                        }
                        else{
                            result_s.setText("Error: " + response.raw());
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        TextView result_s = findViewById(R.id.url_show);
                        result_s.setText("Failure!" + t.getMessage() );
                    }
                });
            }
            else{
                TextView result_s = findViewById(R.id.url_show);
                result_s.setText(value);
            }

        }




    }
}