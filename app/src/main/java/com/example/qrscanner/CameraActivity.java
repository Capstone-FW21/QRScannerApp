package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class CameraActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    boolean CameraPermission = false;
    final int CAMERA_PERM = 1;
    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().setTitle("Scan a valid QR");
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
                            url = result.getText();
                            Log.e("!!!!!", url);
                            if (url.startsWith("personal_scan://")) {
                                Intent getIntent = new Intent(getApplicationContext(), ScanActivity.class);
                                getIntent.putExtra("key", url);
                                getIntent.putExtra("activeEmail", MainActivity.activeEmail);
                                startActivity(getIntent);
                                finish();
                                return;
                            }
                            String[] arr = url.split("\\?",2);
                            String[] arr2 = arr[1].split("=",2);
                            Log.e("arr", arr2[0]);
                            if(arr2[0].compareTo("room_id") == 0) {
                                //Toast.makeText(CameraActivity.this, url, Toast.LENGTH_SHORT).show();
                                Intent getIntent = new Intent(getApplicationContext(), ScanActivity.class);
                                getIntent.putExtra("key", url);
                                getIntent.putExtra("activeEmail", MainActivity.activeEmail);
                                startActivity(getIntent);
                                finish();
                            }
                            else {
                                Toast.makeText(CameraActivity.this, "Invalid QR", Toast.LENGTH_SHORT).show();
                                mCodeScanner.startPreview();
                            }

                        }
                    });

                }
            });
        }

        Button web_btn = findViewById(R.id.open_web);
        web_btn.setOnClickListener(v -> {
            String url = "http://34.134.138.81/?email=" + MainActivity.activeEmail;
            Intent web = new Intent(Intent.ACTION_VIEW);
            web.setData(Uri.parse(url));
            startActivity(web);

        });

    }
    private void askPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);

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

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }
}