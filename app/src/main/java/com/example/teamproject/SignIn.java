package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class SignIn extends AppCompatActivity {

    EditText email, password;
    Button logIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //MongoDB
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        MongoCollection<Document> mongoCollection;
        User user;
        App app;
        String appId = "application-0-yvjts";

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        logIn = findViewById(R.id.logIn);

        mAuth = FirebaseAuth.getInstance();


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task1) {

                        //MongoDB
                        MongoClient mongoClient;
                        MongoDatabase mongoDatabase;
                        MongoCollection<Document> mongoCollection;
                        User user;
                        App app;
                        String appId = "application-0-yvjts";

                        if (task1.isSuccessful()) {

                            if (mAuth.getCurrentUser().isEmailVerified()) {

                                app = new App(new AppConfiguration.Builder(appId).build());
                                user = app.currentUser();
                                mongoClient = user.getMongoClient("mongodb-atlas");
                                mongoDatabase = mongoClient.getDatabase("TeamDB");
                                mongoCollection = mongoDatabase.getCollection("UserEmail");

                                Document queryFilter  = new Document("email", email.getText().toString());
                                mongoCollection.findOne(queryFilter).getAsync(task -> {
                                    if (task.isSuccess()){
                                        if (task.get() == null) {
                                            Document document = new Document().append("userId", user.getId()).append("email", email.getText().toString());
                                            mongoCollection.insertOne(document).getAsync(result -> {
                                            });
                                            Intent i = new Intent(SignIn.this, Profile.class);
                                            i.putExtra("linkId", 23);
                                            startActivity(new Intent(SignIn.this, Profile.class));
                                        }
                                        else if (task.get() != null){
                                            startActivity(new Intent(SignIn.this, Welcome.class));
                                        }
                                    }else{
                                    }
                                });

                            }else{
                                Toast.makeText(SignIn.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignIn.this, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}