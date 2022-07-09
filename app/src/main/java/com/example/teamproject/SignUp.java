package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    ImageView logo;
    EditText email, password, rePassword;
    Button continueBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    /*
    Password must contain at least one digit [0-9].
    Password must contain at least one lowercase Latin character [a-z].
    Password must contain at least one uppercase Latin character [A-Z].
    Password must contain at least one special character like ! @ # & ( ).
    Password must contain a length of at least 8 characters and a maximum of 20 characters.
    */

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        logo = findViewById(R.id.logo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        continueBtn = findViewById(R.id.continueBtn);
        progressDialog = new ProgressDialog(this);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();

            }
        });

    }

    private void PerforAuth() {
        String intEmail = email.getText().toString();
        String intPass = password.getText().toString();
        String intRePass = rePassword.getText().toString();

        if (!intEmail.matches(emailPattern)) {
            email.setError("Enter correct email address");
        } else if (intPass.isEmpty() || !intPass.matches(passwordPattern)) {
            password.setError("Enter proper password");
        } else if (!intPass.equals(intRePass)) {
            rePassword.setError("Password not match");
        }
        else {
            progressDialog.setMessage("Please wait");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(intEmail,intPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignUp.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(this, VerificationCode.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
