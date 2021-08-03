package com.devofindia.naturewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.devofindia.naturewallpaper.Adapters.OfflineAdapter;
import com.devofindia.naturewallpaper.Modals.OfflineModal;
import com.devofindia.naturewallpaper.databinding.ActivityLikedBinding;

import java.util.ArrayList;

public class LikedActivity extends AppCompatActivity {

    ActivityLikedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Liked Images");

        DbSqlHelper helper = new DbSqlHelper(this);
        ArrayList<OfflineModal> mdlist = helper.getWallpaper();

        OfflineAdapter adapter = new OfflineAdapter(mdlist, getApplicationContext());
        binding.rcoffline.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(LikedActivity.this, 2);
        binding.rcoffline.setLayoutManager(gridLayoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}