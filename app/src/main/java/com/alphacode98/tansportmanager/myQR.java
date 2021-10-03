package com.alphacode98.tansportmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class myQR extends AppCompatActivity {
    EditText qrValue;
    ImageView qrCode;
    Button testButton;
    ImageButton doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr);

        qrValue = findViewById(R.id.testInput);
        qrCode = findViewById(R.id.myQrImageView);
        testButton = findViewById(R.id.testBtn);
        doneBtn = findViewById(R.id.doneBtn2);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrValue.getText().toString();
                QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,1000);
                Bitmap qrBits = qrgEncoder.getBitmap();
                qrCode.setImageBitmap(qrBits);

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }
}