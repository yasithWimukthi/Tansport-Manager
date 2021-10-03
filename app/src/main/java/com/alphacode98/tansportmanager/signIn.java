package com.alphacode98.tansportmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.alphacode98.tansportmanager.Util.LoggedUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

public class signIn extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageButton signInBtn;
    private TextView signUpTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection(CommonConstants.USERS_COLLECTION);

    public User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        loggedUser = LoggedUser.getLoggedUser();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInBtn = findViewById(R.id.signInBtn);
        signUpTextView = findViewById(R.id.signUpTxt);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    TastyToast.makeText(
                            getApplicationContext(),
                            "Please enter a valid email !",
                            TastyToast.LENGTH_LONG,
                            TastyToast.ERROR
                    );
                }else if (TextUtils.isEmpty(password)){
                    TastyToast.makeText(
                            getApplicationContext(),
                            "Please enter your password !",
                            TastyToast.LENGTH_LONG,
                            TastyToast.ERROR
                    );
                }else if (password.length()<6){
                    TastyToast.makeText(
                            getApplicationContext(),
                            "Password should contain at least 6 characters !",
                            TastyToast.LENGTH_LONG,
                            TastyToast.INFO
                    );
                }else{
                    loginWithEmailAndPassword(email, password);
                }
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),signUp.class);
                startActivity(intent);
            }
        });
    }

    private void loginWithEmailAndPassword(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        userCollection
                                .whereEqualTo(CommonConstants.EMAIL,email)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(!value.isEmpty()){
                                            for(QueryDocumentSnapshot snapshot : value){
                                                loggedUser.setName(snapshot.getString(CommonConstants.NAME));
                                                break;
                                            }
                                        }
                                    }
                                });

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof FirebaseAuthInvalidCredentialsException){
                            TastyToast.makeText(
                                    getApplicationContext(),
                                    "Invalid password. Please try again !",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR
                            );
                        }else if(e instanceof FirebaseAuthInvalidUserException){
                            TastyToast.makeText(
                                    getApplicationContext(),
                                    "Incorrect email address. Please try again !",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR
                            );
                        }else{
                            TastyToast.makeText(
                                    getApplicationContext(),
                                    e.getLocalizedMessage(),
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR
                            );
                        }
                    }
                });
    }
}