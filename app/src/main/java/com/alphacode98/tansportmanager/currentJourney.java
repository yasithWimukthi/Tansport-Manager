package com.alphacode98.tansportmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class currentJourney extends AppCompatActivity {

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView startTimeTextView;
    private TextView startLocationTextView;
    private ImageButton endTripBtn;
    private ImageButton backToHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_current_journey);

        nameTextView = findViewById(R.id.name);
        dateTextView = findViewById(R.id.dateValue);
        startTimeTextView = findViewById(R.id.startTimeValue);
        startLocationTextView = findViewById(R.id.startLocationValue);
        endTripBtn = findViewById(R.id.endTripBtn);
        backToHomeBtn = findViewById(R.id.doneBtn);

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