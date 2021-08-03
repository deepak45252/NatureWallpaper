package com.devofindia.naturewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebViewClient;

import com.devofindia.naturewallpaper.databinding.ActivityPrivacyPolicyBinding;

public class Privacy_policy extends AppCompatActivity {

    ActivityPrivacyPolicyBinding binding;
    String url = "https://nature-wallpaper-3.flycricket.io/privacy.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.wbview.setWebViewClient(new WebViewClient());
        binding.wbview.getSettings().setJavaScriptEnabled(true);
        binding.wbview.loadUrl(url);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}