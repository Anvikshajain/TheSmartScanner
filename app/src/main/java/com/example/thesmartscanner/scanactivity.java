package com.example.thesmartscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class scanactivity extends AppCompatActivity{

    public CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    public static String resultData;
    public static String[] dataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanactivity);
        scannerView=findViewById(R.id.scanner_view);
        mCodeScanner=new CodeScanner(this,scannerView);

        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            resultData=result.getText();
            if(validate()) {
                startActivity(new Intent(scanactivity.this, detailsactivity.class));
            }else{
                Toast.makeText(scanactivity.this,"Invalid QR Code",Toast.LENGTH_SHORT).show();
            }
        }));
        scannerView.setOnClickListener(v -> mCodeScanner.startPreview());
    }

    public boolean validate(){
        boolean value=false;
        dataArray=getResult();
        try{
            if(dataArray.length>1){
                value=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mCodeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(scanactivity.this,"Camera Permission is required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    public static String[] getResult(){
        dataArray=resultData.split("[,:]");
        return dataArray;
    }

}