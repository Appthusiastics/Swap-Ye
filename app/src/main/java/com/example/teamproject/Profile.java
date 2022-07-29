package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Profile extends AppCompatActivity {

    Button save;
    EditText nickname;

    //MongoDB
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    User user;
    App app;
    String appId = "application-0-yvjts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nickname = findViewById(R.id.nickname);

        /*
        app = new App(new AppConfiguration.Builder(appId).build());
        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Toast.makeText(Profile.this, "Login is successful.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

         */

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("TeamDB");
                mongoCollection = mongoDatabase.getCollection("UserEmail");

                Document document = new Document().append("userId", user.getId()).append("nickName", nickname.getText().toString());

                mongoCollection.insertOne(document).getAsync(result -> {
                    if (result.isSuccess()) {
                        Toast.makeText(Profile.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Profile.this, "Error " + result.getError(), Toast.LENGTH_LONG).show();
                    }


                });

                Intent myInt = new Intent(getApplicationContext(), Welcome.class);
                startActivity(myInt);
            }

        });




    }



    }


