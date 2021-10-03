package com.alphacode98.tansportmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alphacode98.tansportmanager.Modal.User;
import com.alphacode98.tansportmanager.Util.LoggedUser;


public class MainActivity extends AppCompatActivity {
    ImageButton scanQrBtn,myQrBtn;

    private TextView usernameTextView;
    private User loggedUser;
    private ImageButton topUpBtn;
    private ImageButton currentJourneyBtn;
    private TextView balanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        scanQrBtn = findViewById(R.id.scanQrBtn);
        myQrBtn = findViewById(R.id.myQrBtn);


                loggedUser = LoggedUser.getLoggedUser();

                usernameTextView = findViewById(R.id.usernameTextView);
                topUpBtn = findViewById(R.id.topUpBtn);
                currentJourneyBtn = findViewById(R.id.currentJourneyBtn);
                balanceTextView = findViewById(R.id.balanceText);

                usernameTextView.setText(loggedUser.getName());
                balanceTextView.setText(String.valueOf(loggedUser.getAmount()));

                topUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplication(), topUp.class);
                        startActivity(intent);
                    }
                });

                currentJourneyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplication(), currentJourney.class);
                        startActivity(intent);

                    }
                });

                myQrBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), myQR.class));
                    }
                });

                scanQrBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                      public void onClick(View v) {
                          startActivity(new Intent(getApplicationContext(), startJourney.class));
                     }
                    });
            }
        }
