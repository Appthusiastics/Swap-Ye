package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class ListAds extends AppCompatActivity {

    Ad ad = new Ad();

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    User user;
    App app;
    String appId = "application-0-yvjts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ads);

        app = new App(new AppConfiguration.Builder(appId).build());
        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("TeamDB");
        mongoCollection = mongoDatabase.getCollection("Ad");

        Bundle extras = getIntent().getExtras();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        if (extras != null) {
            String passEmail = extras.getString("linkEmail");

            Document queryFilter = new Document("email", passEmail.toString());
            RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();
            findTask.getAsync(task -> {
                if (task.isSuccess()) {

                    List<Ad> ads = new ArrayList<>();

                        MongoCursor<Document> results = task.get();
                        while (results.hasNext()) {
                            ads.add(new Ad(results.next().getString("title"), "Description will be here", R.drawable.second_hand));

                            //ad.setTitle(results.next().getString("title"));
                            //ad.setDesc(results.next().getString("description"));

                            Log.v("EXAMPLE", results.toString());

                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            recyclerView.setAdapter(new MyAdapter(getApplicationContext(), ads));
                        }
                    } else {
                        Log.e("EXAMPLE", "failed to find documents with: ", task.getError());
                    }
            });
        }

    }
}