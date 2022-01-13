package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getivity extends AppCompatActivity {

    private TextView textviewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getivity);
        Bundle extras = getIntent().getExtras(); //get QR from main activities

        /*if (extras != null) {
            String value = extras.getString("key");
            TextView result_s = findViewById(R.id.url_show);
            result_s.setText(value);
        }*/

        textviewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://contact-api-dev-3sujih4x4a-uc.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Get_interface getinterface = retrofit.create(Get_interface.class);

        //Call<List<Post>> call = jpha.getStudent();
        Call<Get> call = getinterface.getStudent();


        /*call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    textviewResult.setText("Code: " + response.code());

                    return;
                }

                List<Post> posts = response.body();


                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "UID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";


                    textviewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textviewResult.setText(t.getMessage());

            }
        });*/
        call.enqueue(new Callback<Get>() {
            @Override
            public void onResponse(Call<Get> call, Response<Get> response) {

                if(!response.isSuccessful()){
                    textviewResult.setText("Code: " + response.code());

                    return;
                }

                Get get = response.body();


                //for (Post post : posts){
                String content = "";
                    /*content += "ID: " + post.getId() + "\n";
                    content += "UID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";*/
                content += "First: " + get.getFirst_name() + "\n";
                content += "Last: " + get.getLast_name() + "\n";
                content += "Email: " + get.getEmail() + "\n\n";


                textviewResult.append(content);
                //}

            }

            @Override
            public void onFailure(Call<Get> call, Throwable t) {
                textviewResult.setText(t.getMessage());

            }
        });

    }
}