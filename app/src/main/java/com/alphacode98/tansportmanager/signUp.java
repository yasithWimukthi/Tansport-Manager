package com.alphacode98.tansportmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alphacode98.tansportmanager.Modal.User;
import com.alphacode98.tansportmanager.Util.CommonConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdsmdg.tastytoast.TastyToast;

public class signUp extends AppCompatActivity {

    private EditText nameEditText;
    private EditText nicEditText;
    private EditText mobileEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText passwordEditText;
    private ImageButton signUpBtn;
    private TextView signInTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection(CommonConstants.USERS_COLLECTION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.nameEditText);
        nicEditText = findViewById(R.id.nicEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInTextView = findViewById(R.id.signInTextView);

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),signIn.class);
                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String nic = nicEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                if (TextUtils.isEmpty(nameEditText.getText().toString().trim()) ||
                        TextUtils.isEmpty(nicEditText.getText().toString().trim())||
                        TextUtils.isEmpty(emailEditText.getText().toString().trim()) ||
                        TextUtils.isEmpty(passwordEditText.getText().toString().trim()) ||
                        TextUtils.isEmpty(mobileEditText.getText().toString().trim()) ||
                        TextUtils.isEmpty(addressEditText.getText().toString().trim())
                ){
                    TastyToast.makeText(
                            getApplicationContext(),
                            "Please enter correct values!",
                            TastyToast.LENGTH_LONG,
                            TastyToast.ERROR
                    );
                }else{
                    addUser(name,nic,email,password,mobile,address);
                }
            }
        });
    }

    private void addUser(String name, String nic, String email, String password, String mobile, String address) {
        User user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setNic(nic);

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userCollection.add(user)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
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
}