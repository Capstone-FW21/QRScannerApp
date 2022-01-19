package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.Map;


public class getivity extends AppCompatActivity {

    private TextView data_display;
    private String first_name;
    private String last_name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getivity);
        getSupportActionBar().setTitle("Account Manager");

        //check if existing saved account exist
        File f = new File(
                "/data/data/com.example.qrscanner/shared_prefs/account_list.xml");
        if (f.exists()) {
            Button btn_login = findViewById(R.id.login2existing);
            btn_login.setVisibility(View.VISIBLE);
            show_accounts();
        }

        Button btn_new_account = findViewById(R.id.create_acc_btn);
        btn_new_account.setOnClickListener(v -> {
            create_account();
        });

        Button btn_login = findViewById(R.id.login2existing);
        btn_login.setOnClickListener(v -> {
            if(first_name == null && last_name == null)
                Toast.makeText(getivity.this, "Please Select An Account First!", Toast.LENGTH_LONG).show();
            else
                send_back();
        });


    }

    public void show_accounts(){
        LinearLayout layout = findViewById(R.id.linear_layout_btns);
        SharedPreferences accountList = getSharedPreferences("account_list", MODE_PRIVATE);
        Map<String, ?> sp = accountList.getAll();
        Button btn_login = findViewById(R.id.login2existing);

        for (Map.Entry<String, ?> entry : sp.entrySet()) {
            Button btn = new Button(this);
            btn.setOnClickListener(getOnClickDoSomething(btn_login, entry.getKey(),entry.getValue().toString()));
            btn.setText(entry.getValue().toString());
            layout.addView(btn);
        }
    }
    View.OnClickListener getOnClickDoSomething(Button btn, String name, String email2)  {
        return v -> {
            String[] arr = name.split("_",2);
            first_name = arr[0];
            last_name = arr[1];
            email = email2;
            btn.setText("Login as " + arr[0] + " " + arr[1]);
        };
    }


    public void create_account() {

        data_display = findViewById(R.id.text_view_result);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://contact-api-dev-3sujih4x4a-uc.a.run.app/student";

        StringRequest studentRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        //parse the response
                        JSONObject student = new JSONObject(response);
                        first_name = student.getString("first_name");
                        last_name = student.getString("last_name");
                        email = student.getString("email");
                        data_display.setText("Name: " + first_name + " " + last_name + "\nEmail: " + email);

                        //save account to locally
                        SharedPreferences sharedPreferences = getSharedPreferences("account_list", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(first_name + "_" + last_name, email);
                        editor.commit();

                        send_back();

                    } catch (JSONException e) {
                        data_display.setText(e.getMessage());
                    }
                }, error -> data_display.setText("Error: " + error.getMessage()));

        queue.add(studentRequest);
    }

    public void send_back(){
        Intent result = new Intent();
        result.putExtra("first", first_name);
        result.putExtra("last", last_name);
        result.putExtra("email", email);
        setResult(RESULT_OK, result);
        finish();
    }
}
