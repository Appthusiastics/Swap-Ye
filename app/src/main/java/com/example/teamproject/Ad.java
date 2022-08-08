package com.example.teamproject;

import android.widget.ImageView;
import io.realm.RealmObject;

public class Ad {

    String title;
    String desc;
    int image;

    public Ad(String title, String desc, int image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public Ad() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
