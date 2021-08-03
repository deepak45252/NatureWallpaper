package com.devofindia.naturewallpaper.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devofindia.naturewallpaper.FullImageActivity;
import com.devofindia.naturewallpaper.Modals.WallpaperModal;
import com.devofindia.naturewallpaper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.viewholder>{

    ArrayList<WallpaperModal> modalArrayList;
    Context context;

    public WallpaperAdapter(ArrayList<WallpaperModal> modalArrayList, Context context) {
        this.modalArrayList = modalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_images, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Glide.with(context).load(modalArrayList.get(position).getMediumUrl()).placeholder(R.drawable.progress_animation).error(R.drawable.sorry).into(holder.wpimage);
//        Picasso.get().load(modalArrayList.get(position).getMediumUrl()).placeholder(R.drawable.progress_animation).into(holder.wpimage);

        holder.wpimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //you have used adapter in fragment that is why this is required.
                //Anything else??
                intent.putExtra("orignalUrl", modalArrayList.get(position).getOrignalUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modalArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView wpimage;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            wpimage= itemView.findViewById(R.id.spimage);
        }
    }
}
