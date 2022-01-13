package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

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

        Bundle extras = getIntent().getExtras(); //get QR from main activities
        String value ="";
        String email ="";
        String room ="";

        if (extras != null) {
            value = extras.getString("key");

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

                //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("POST");
                //connection.setRequestProperty("Accept","application/json");
                //connection.setDoOutput(true);
                //connection.connect();

            } catch (Exception e) {
                value = e.getMessage();
                value += " or no QR scanned, go back and try again";
            }

            if(email != null && room != null){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://contact-api-dev-3sujih4x4a-uc.a.run.app/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Post_interface post_interface = retrofit.create(Post_interface.class);

                Post post = new Post(email,room+"/");
                Log.e("email",post.getEmail());
                Log.e("room",post.getRoom());

                Call<Post> call = post_interface.createPost(post);

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        TextView result_s = findViewById(R.id.url_show);
                        if(response.isSuccessful()){
                            result_s.setText("Data added with server response: " + response.code());
                            return;
                        }
                        else{
                            result_s.setText("Error: " + response.code());
                            return;
                        }


                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        TextView result_s = findViewById(R.id.url_show);
                        result_s.setText("Failure!" + t.getMessage() );
                    }
                });
            }

        }

        //TextView result_s = findViewById(R.id.url_show);
        //result_s.setText(value);


    }
}