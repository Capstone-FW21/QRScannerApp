package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {

    private String url = null;
    private String name = null;
    private CodeScanner mCodeScanner;
    boolean CameraPermission = false;
    final int CAMERA_PERM = 1;
    private String active_email = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Scan a room QR code");
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        askPermission();
        if (CameraPermission) {

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCodeScanner.startPreview();

                }
            });


            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                            url = result.getText();
                            String[] arr = url.split("=",2);

                            if(name == null)
                                getSupportActionBar().setTitle("who" + " visits " + arr[1] + "??");
                            else
                                getSupportActionBar().setTitle(name + " visits " + arr[1]);
                        }
                    });

                }
            });
        }

        Button btn_get = findViewById(R.id.get_btn);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(getApplicationContext(), getivity.class);
                getIntent.putExtra("key", url);
                startActivityForResult(getIntent,111);
            }
        });

        Button btn_post = findViewById(R.id.post_btn);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(active_email == null)
                    Toast.makeText(MainActivity.this, "Please Select An Account First!", Toast.LENGTH_LONG).show();
                else if(url == null)
                    Toast.makeText(MainActivity.this, "Please Scan a room QR first", Toast.LENGTH_LONG).show();
                else {
                    Intent getIntent = new Intent(getApplicationContext(), postivity.class);
                    getIntent.putExtra("key", url);
                    getIntent.putExtra("email", active_email);
                    startActivity(getIntent);

                }
            }
        });


        /*btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                EditText edit = new EditText(MainActivity.this);
                alert.setView(edit);
                alert.setTitle("Enter Email!");
                alert.setPositiveButton("Submit Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent getIntent = new Intent(getApplicationContext(), postivity.class);
                        getIntent.putExtra("key", url);
                        //getIntent.putExtra("email", edit.getText().toString());
                        getIntent.putExtra("email", active_email);

                        startActivity(getIntent);
                        dialogInterface.dismiss();
                    }
                });
                alert.show();

            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 111) {
            if (data.hasExtra("email")) {
                String name = data.getExtras().getString("first") + " " + data.getExtras().getString("last");
                getSupportActionBar().setTitle(name);
                this.name = name;
                active_email = data.getExtras().getString("email");
            }
        }
    }

    private void askPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);

            } else {
                mCodeScanner.startPreview();
                CameraPermission = true;
            }

        }
    }


    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}