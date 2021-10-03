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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alphacode98.tansportmanager.Modal.User;
import com.alphacode98.tansportmanager.Util.Location;
import com.alphacode98.tansportmanager.Util.LoggedUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class eBill extends AppCompatActivity {

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView startLocationTextView;
    private TextView endLocationTextView;
    private TextView distanceTextView;
    private TextView amountTextView;
    private ImageButton doneBtn;

    private User loggedUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FusedLocationProviderClient locationProviderClient;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ebill);

        loggedUser = LoggedUser.getLoggedUser();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        nameTextView = findViewById(R.id.passengerValue);
        dateTextView = findViewById(R.id.dateValue);
        startTimeTextView = findViewById(R.id.start);
        endTimeTextView = findViewById(R.id.endTimeValue);
        startLocationTextView = findViewById(R.id.startLocationValue);
        endLocationTextView = findViewById(R.id.endLocationValue);
        distanceTextView = findViewById(R.id.distanceValue);
        amountTextView = findViewById(R.id.amountValue);
        doneBtn = findViewById(R.id.doneBtn);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        String date = String.valueOf(java.time.LocalDate.now());
        dateTextView.setText(date);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        endTimeTextView.setText(dtf.format(localTime));
        nameTextView.setText(loggedUser.getName());
        startTimeTextView.setText(sh.getString("startTime",""));
        startLocationTextView.setText(sh.getString("startLocation",""));

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });

        getDestination();
        //calculateCost(startLocationTextView.getText().toString(),endLocationTextView.getText().toString());
        //updateUser();
    }

    private void updateUser() {
        // update user credit balance
        final User[] user = {new User()};
        DocumentReference docRef = db.collection("users").document(loggedUser.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user[0] = documentSnapshot.toObject(User.class);
            }
        });

        user[0].setAmount(user[0].getAmount() - Float.parseFloat(amountTextView.getText().toString()));

        db.collection("users").add(user[0]);
    }

    private void getDestination() {
        if (ActivityCompat.checkSelfPermission(eBill.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                @Override
                public void onComplete(@NonNull Task<android.location.Location> task) {
                    android.location.Location location = task.getResult();
                    if (location != null){
                        try {
                            // initialize geocoder
                            Geocoder geocoder = new Geocoder(eBill.this, Locale.getDefault());
                            //initialize address list
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            endLocationTextView.setText(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(eBill.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }


    private void calculateCost(String startLocation, String destination) {
        final Location[] start = {new Location()};
        final Location[] end = {new Location()};
        DocumentReference docRef = db.collection("cities").document(startLocation);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                start[0] = documentSnapshot.toObject(Location.class);
            }
        });

        docRef = db.collection("cities").document(destination);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                end[0] = documentSnapshot.toObject(Location.class);
            }
        });

        // calculate cost
        float cost = start[0].getCost() - end[0].getCost();
        if (cost < 0) cost = -1 * cost;
        amountTextView.setText(Float.toString(cost));

        // calculate distance
        float distance = start[0].getDistance() - end[0].getDistance();
        if (distance < 0) distance = -1 * distance;
        distanceTextView.setText(Float.toString(distance));
    }
}