package com.example.teamproject;

import android.widget.ImageView;

public class Ad {

    String name;
    String desc;
    ImageView imageView;

    public Ad(){

    }

    public Ad(String name, String desc, ImageView imageView) {
        this.name = name;
        this.desc = desc;
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
