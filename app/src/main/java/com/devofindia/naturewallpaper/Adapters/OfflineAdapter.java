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
import com.devofindia.naturewallpaper.Modals.OfflineModal;
import com.devofindia.naturewallpaper.R;


import java.util.ArrayList;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.offlineViewholder> {
    ArrayList<OfflineModal> list;
    Context context;

    public OfflineAdapter(ArrayList<OfflineModal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public offlineViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_images, parent, false);
        return new offlineViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull offlineViewholder holder, int position) {
        OfflineModal modal = list.get(position);
        Glide.with(context).load(list.get(position).getSamplewallpaper()).placeholder(R.drawable.user).into(holder.smimage);
        holder.smimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orignalUrl", list.get(position).getSamplewallpaper());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class offlineViewholder extends RecyclerView.ViewHolder {
        ImageView smimage;
        public offlineViewholder(@NonNull View itemView) {
            super(itemView);
            smimage= itemView.findViewById(R.id.spimage);
        }
    }
}
