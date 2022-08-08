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

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class CreateAd extends AppCompatActivity {

    UserInfo usr = new UserInfo();

    String takeEmail;

    EditText title;
    EditText desc;
    Button save;
    Button profile;

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    User user;
    App app;
    String appId = "application-0-yvjts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        title = findViewById(R.id.addTitle);
        desc = findViewById(R.id.addDesc);

        save = findViewById(R.id.addSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                app = new App(new AppConfiguration.Builder(appId).build());
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("TeamDB");
                mongoCollection = mongoDatabase.getCollection("Ad");

                Bundle extras = getIntent().getExtras();
                if (extras != null) {

                    String passEmail = extras.getString("linkEmail");
                    String passNickname = extras.getString("linkNickname");
                    Document document = new Document().append("email", passEmail).append("nickname", passNickname).append("title", title.getText().toString()).append("description", desc.getText().toString());

                    mongoCollection.insertOne(document).getAsync(result -> {

                        if (result.isSuccess()) {
                            Toast.makeText(CreateAd.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                            title.setText("");
                            desc.setText("");
                        }
                    });
                }




            }

        });

        profile = findViewById(R.id.goProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }


}