package com.alphacode98.tansportmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alphacode98.tansportmanager.Modal.User;
import com.alphacode98.tansportmanager.Util.LoggedUser;

public class currentJourney extends AppCompatActivity {

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView startTimeTextView;
    private TextView startLocationTextView;
    private ImageButton endTripBtn;
    private ImageButton backToHomeBtn;

    private User loggedUser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_current_journey);

        loggedUser = LoggedUser.getLoggedUser();

        nameTextView = findViewById(R.id.name);
        dateTextView = findViewById(R.id.dateValue);
        startTimeTextView = findViewById(R.id.startTimeValue);
        startLocationTextView = findViewById(R.id.startLocationValue);
        endTripBtn = findViewById(R.id.endTripBtn);
        backToHomeBtn = findViewById(R.id.doneBtn);

        // get shared preferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        startTimeTextView.setText(sh.getString("startTime",""));
        startLocationTextView.setText(sh.getString("startLocation",""));

        String date = String.valueOf(java.time.LocalDate.now());
        dateTextView.setText(date);

        nameTextView.setText(loggedUser.getName());

        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });

        endTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),eBill.class);
                startActivity(intent);
            }
        });
    }
}