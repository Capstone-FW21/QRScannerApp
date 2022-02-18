package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;


public class AccountActivity extends AppCompatActivity {

    private TextView data_display;
    private String first_name;
    private String last_name;
    private String email;
    private int id;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setTitle("Account Manager");
        pbar = findViewById(R.id.pBar);

        //check if existing saved account exist
        File f = new File(
                "/data/data/com.example.qrscanner/shared_prefs/account_list.xml");
        if (f.exists()) {
            Button btn_login = findViewById(R.id.login2existing);
            btn_login.setVisibility(View.VISIBLE);
            show_accounts();
        } else {
            Button btn_login = findViewById(R.id.login2existing);
            btn_login.setVisibility(View.GONE);
        }

        Button btn_new_account = findViewById(R.id.create_acc_btn);
        btn_new_account.setOnClickListener(v -> {
            pbar.setProgress(25);
            create_account();
            Toast.makeText(AccountActivity.this, "Account Created!", Toast.LENGTH_LONG).show();
        });

        Button btn_login = findViewById(R.id.login2existing);
        btn_login.setOnClickListener(v -> {
            if (first_name == null && last_name == null)
                Snackbar.make(v, "Select an account first!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else
                send_back();
        });


    }

    public void show_accounts() {
        LinearLayout layout = findViewById(R.id.linear_layout_btns);
        SharedPreferences accountList = getSharedPreferences("account_list", MODE_PRIVATE);
        Map<String, ?> sp = accountList.getAll();
        Button btn_login = findViewById(R.id.login2existing);

        for (Map.Entry<String, ?> entry : sp.entrySet()) {
            Button btn = new Button(this);
            btn.setOnClickListener(getOnClickDoSomething(btn_login, entry.getKey(), entry.getValue().toString()));
            btn.setText(entry.getValue().toString());
            layout.addView(btn);
        }
    }

    View.OnClickListener getOnClickDoSomething(Button btn, String name, String email2) {
        return v -> {
            String[] arr = name.split("_", 3);
            first_name = arr[0];
            last_name = arr[1];
            email = email2;

            id = Integer.parseInt(arr[2]);

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
                        id = student.getInt("personal_id");
                        first_name = student.getString("first_name");
                        last_name = student.getString("last_name");
                        email = student.getString("email");

                        //data_display.setText("Name: " + first_name + " " + last_name + "\nEmail: " + email+ "\nid: " + id);


                        //save account to locally
                        SharedPreferences sharedPreferences = getSharedPreferences("account_list", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(first_name + "_" + last_name + "_" + id, email);
                        editor.commit();

                        pbar.setProgress(100);
                        send_back();

                    } catch (JSONException e) {
                        data_display.setText(e.getMessage());
                    }
                }, error -> data_display.setText("Error: " + error.getMessage()));
        pbar.setProgress(50);
        queue.add(studentRequest);
    }

    public void send_back() {
        Intent result = new Intent();
        result.putExtra("first", first_name);
        result.putExtra("last", last_name);
        MainActivity.activeEmail = email;
        //Log.e("????", MainActivity.activeEmail);
        MainActivity.activeId = id;
        setResult(RESULT_OK, result);
        set_last_log();
        finish();
    }

    public void set_last_log(){
        SharedPreferences last_log = getSharedPreferences("last_log", MODE_PRIVATE);
        last_log.edit().clear().commit();
        SharedPreferences.Editor editor = last_log.edit();
        editor.putString(first_name + "_" + last_name + "_" + id, email);
        editor.commit();
    }

}
