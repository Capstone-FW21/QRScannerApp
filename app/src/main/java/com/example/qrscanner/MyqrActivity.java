package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MyqrActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.darklight == 1){
            setTheme(R.style.Dark);
            setContentView(R.layout.activity_myqr);
        }
        else{
            setTheme(R.style.Light);
            setContentView(R.layout.activity_myqr);
        }

        setContentView(R.layout.activity_myqr);

        ImageView myqr = findViewById(R.id.myqr_pic);
        MultiFormatWriter mfw = new MultiFormatWriter();
        getSupportActionBar().setTitle("Scan me");
        try{

            BitMatrix bitMatrix = mfw.encode("personal_scan://" + MainActivity.activeEmail, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            myqr.setImageBitmap(bitmap);

        }catch (Exception e) {

            e.printStackTrace();

        }

        Button finish_btn = findViewById(R.id.done_btn);
        finish_btn.setOnClickListener(v -> {
            finish();
        });



    }
}