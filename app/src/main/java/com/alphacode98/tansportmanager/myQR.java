package com.alphacode98.tansportmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alphacode98.tansportmanager.Modal.User;
import com.alphacode98.tansportmanager.Util.LoggedUser;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class myQR extends AppCompatActivity {
    private ImageView qrCode;
    private ImageButton doneBtn;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_qr);

        loggedUser = LoggedUser.getLoggedUser();
        qrCode = findViewById(R.id.myQrImageView);
        doneBtn = findViewById(R.id.doneBtn2);

        QRGEncoder qrgEncoder = new QRGEncoder("loggedUser.getEmail()",null, QRGContents.Type.TEXT,1000);
        Bitmap qrBits = qrgEncoder.getBitmap();
        qrCode.setImageBitmap(qrBits);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}