package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Welcome extends AppCompatActivity {

    UserInfo usr = new UserInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button listAds;
        Button createAd;
        TextView getNickname;

        //MongoDB
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        MongoCollection<Document> mongoCollection;
        User user;
        App app;
        String appId = "application-0-yvjts";

        listAds = findViewById(R.id.listAds);
        createAd = findViewById(R.id.createAd);
        getNickname = findViewById(R.id.getNickname);

        app = new App(new AppConfiguration.Builder(appId).build());
        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("TeamDB");
        mongoCollection = mongoDatabase.getCollection("Profile");

        Bundle extras = getIntent().getExtras();



        if (extras != null) {
            String passEmail = extras.getString("linkEmail");
            Document queryFilter = new Document("email", passEmail.toString());
            mongoCollection.findOne(queryFilter).getAsync(task -> {
                if (task.isSuccess()) {

                    getNickname.setText(task.get().get("nickname", ""));

                }

            });

            listAds = findViewById(R.id.listAds);
            listAds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Welcome.this, CreateAd.class);
                    i.putExtra("linkEmail", passEmail.toString());
                    startActivity(i);

                }
            });
            createAd = findViewById(R.id.createAd);
            createAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent myInt = new Intent(getApplicationContext(), CreateAd.class);
                    startActivity(myInt);
                }
            });


        }




    }

}