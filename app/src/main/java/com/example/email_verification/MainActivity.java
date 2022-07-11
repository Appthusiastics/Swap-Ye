package com.example.email_verification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendVerificationEmail(View view) {
        Random random=new Random();
        code= random.nextInt(8999)+1000;
        EditText emailTXT=findViewById(R.id.email);

    }
}