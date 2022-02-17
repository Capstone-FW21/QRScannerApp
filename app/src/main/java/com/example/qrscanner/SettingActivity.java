package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("Settings (WIP)");
        SharedPreferences accountList = getSharedPreferences("account_list", MODE_PRIVATE);


        Button rm_acc_btn = findViewById(R.id.rm_accounts);
        rm_acc_btn.setOnClickListener(v -> {

            //Toast.makeText(SettingActivity.this, "Account Created!", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Confirm all account deletion");
            builder.setMessage("WIP, will not delete account yet");
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                //Toast.makeText(SettingActivity.this, "Confirmed", Toast.LENGTH_SHORT).show();

                if(MainActivity.name == null) {
                    accountList.edit().clear().commit();
                    finish();
                }
                else{
                    accountList.edit().clear().commit();
                    String arr[] = MainActivity.name.split(" ",2);
                    SharedPreferences.Editor editor = accountList.edit();
                    editor.putString(arr[0] + "_" + arr[1] + "_" + MainActivity.activeId, MainActivity.activeEmail);
                    editor.commit();
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(SettingActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}