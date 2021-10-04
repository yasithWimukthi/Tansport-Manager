package com.alphacode98.tansportmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alphacode98.tansportmanager.Util.CommonConstants;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.Result;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class startJourney extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    TextView resultData;
    private FusedLocationProviderClient locationProviderClient;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start_journey);
        resultData = findViewById(R.id.resultsOfQr);
        CodeScannerView scannerView = findViewById(R.id.scannerView);
        mCodeScanner = new CodeScanner(this, scannerView);
        mp = MediaPlayer.create(this, R.raw.notification);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        mp.start();
                        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstants.SHARED_PREFERENCES,MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.TIME_FORMAT);
                        LocalTime localTime = LocalTime.now();
                        myEdit.putString(CommonConstants.START_TIME,dtf.format(localTime));
                        //myEdit.putInt(CommonConstants.ROUTE,Integer.parseInt(result.getText()));
                        myEdit.commit();
                        getStartLocation();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void getStartLocation() {
        if (ActivityCompat.checkSelfPermission(startJourney.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    android.location.Location location = task.getResult();
                    if (location != null){
                        try {
                            // initialize geocoder
                            Geocoder geocoder = new Geocoder(startJourney.this, Locale.getDefault());
                            //initialize address list
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            SharedPreferences sharedPreferences = getSharedPreferences(CommonConstants.SHARED_PREFERENCES,MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString(CommonConstants.START_LOCATION,addresses.get(0).getAddressLine(0));
                            myEdit.commit();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(startJourney.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }
}


