package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Ad> ads;

    public MyAdapter(Context context, List<Ad> ads) {
        this.context = context;
        this.ads = ads;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.ad_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleView.setText(ads.get(position).getTitle());
        holder.descView.setText(ads.get(position).getDesc());
        holder.imageView.setImageResource(ads.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }
}
