package com.alphacode98.tansportmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ebill);

        nameTextView = findViewById(R.id.passengerValue);
        dateTextView = findViewById(R.id.dateValue);
        startTimeTextView = findViewById(R.id.start);
        endTimeTextView = findViewById(R.id.endTimeValue);
        startLocationTextView = findViewById(R.id.startLocationValue);
        endLocationTextView = findViewById(R.id.endLocationValue);
        distanceTextView = findViewById(R.id.distanceValue);
        amountTextView = findViewById(R.id.amountValue);
        doneBtn = findViewById(R.id.doneBtn);

        String time = String.valueOf(java.time.LocalDate.now());
        dateTextView.setText(time);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        endTimeTextView.setText(dtf.format(localTime));

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}