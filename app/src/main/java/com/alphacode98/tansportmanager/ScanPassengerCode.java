package com.alphacode98.tansportmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alphacode98.tansportmanager.Modal.Fine;
import com.alphacode98.tansportmanager.Util.CommonConstants;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;
import com.sdsmdg.tastytoast.TastyToast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScanPassengerCode extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private EditText reasonEditText;
    private EditText amountEditText;
    private ImageButton addFineBtn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scan_passanger_code);

        reasonEditText = findViewById(R.id.reasonEditText);
        amountEditText = findViewById(R.id.amountEditText);
        addFineBtn = findViewById(R.id.addFineBtn);
        CodeScannerView scannerView = findViewById(R.id.scannerView);
        mCodeScanner = new CodeScanner(this, scannerView);

        // SCAN PASSENGER'S QR CODE
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                SharedPreferences sharedPreferences = getSharedPreferences(CommonConstants.SHARED_PREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString(CommonConstants.PASSENGER_EMAIL,result.getText());
                myEdit.commit();

            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        addFineBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(reasonEditText.getText().toString().trim())){
                    TastyToast.makeText(
                            getApplicationContext(),
                            "Please enter a reason!",
                            TastyToast.LENGTH_LONG,
                            TastyToast.ERROR
                    );
                }else if (TextUtils.isEmpty(amountEditText.getText().toString().trim())){
                    TastyToast.makeText(
                            getApplicationContext(),
                            "Please enter an amount!",
                            TastyToast.LENGTH_LONG,
                            TastyToast.ERROR
                    );
                }else{
                    String reason = reasonEditText.getText().toString().trim();
                    Float amount = Float.parseFloat(amountEditText.getText().toString().trim());
                    saveFine(reason, amount);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveFine(String reason, Float amount) {
        CollectionReference fineCollection = db.collection(CommonConstants.FINES_COLLECTION);
        Fine fine = new Fine();

        String date = String.valueOf(java.time.LocalDate.now());
        fine.setDate(date);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.TIME_FORMAT);
        LocalTime localTime = LocalTime.now();
        fine.setTime(dtf.format(localTime));

        SharedPreferences sh = getSharedPreferences(CommonConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        fine.setClientEmail(sh.getString(CommonConstants.PASSENGER_EMAIL,""));
        fine.setRootId(sh.getInt(CommonConstants.ROUTE,0));
        fine.setInspectorId(sh.getString(CommonConstants.EMAIL,""));

        fine.setFine(amount);
        fine.setReason(reason);

        fineCollection.add(fine)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        startActivity(new Intent(getApplicationContext(), FineInserted.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        TastyToast.makeText(
                                getApplicationContext(),
                                e.getLocalizedMessage(),
                                TastyToast.LENGTH_LONG,
                                TastyToast.ERROR
                        );
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
}
