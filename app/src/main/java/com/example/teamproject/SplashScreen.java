package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        timeOut();
    }
    void timeOut(){ Thread timerThread = new Thread(){
        public void run(){
            try{

                synchronized(this){
                    wait(2500);
                }

            }catch(InterruptedException e){
                e.printStackTrace();
            }finally{
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    };
        timerThread.start();
    }
}