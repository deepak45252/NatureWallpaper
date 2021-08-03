package com.devofindia.naturewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devofindia.naturewallpaper.databinding.ActivityFullImageBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


import java.io.File;
import java.io.IOException;

public class FullImageActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFullImageBinding binding;
    ImageView fullImage;
    String orignalUrl;
    DbSqlHelper helper;
    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Full Image");

        fullImage = findViewById(R.id.fullimage);
        helper = new DbSqlHelper(this);


        orignalUrl = getIntent().getStringExtra("orignalUrl");
        binding.floatingActionsMenu.setVisibility(View.VISIBLE);
        Glide.with(this).load(orignalUrl).placeholder(R.drawable.progress_animation).error(R.drawable.sorry).into(binding.fullimage);


      binding.like.setOnClickListener(this);
      binding.btnset.setOnClickListener(this);
      binding.sharebtn.setOnClickListener(this);
      binding.download.setOnClickListener(this);

     implemetBannerAdd();

    }

    private void implemetBannerAdd() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }


    @Override
    public void onClick(View v) {
        if (v==binding.btnset){
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            Bitmap bitmap = ((BitmapDrawable)fullImage.getDrawable()).getBitmap();
            try {
                Toast.makeText(FullImageActivity.this, "Wallpaper set Successfully", Toast.LENGTH_SHORT).show();
                wallpaperManager.setBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (v==binding.like){
            helper.insertWallpaper(orignalUrl.toString());
            Toast.makeText(this, "Image Added Liked", Toast.LENGTH_SHORT).show();
        }

        else if (v==binding.sharebtn){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                BitmapDrawable drawable = (BitmapDrawable) binding.fullimage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String bitmappath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
                Uri uri = Uri.parse(bitmappath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(intent.EXTRA_STREAM, uri);
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.devofindia.rahatindorishairy");
                startActivity(Intent.createChooser(intent, "share shairy image"));

            }
            else {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
            }
        }
        else if (v==binding.download){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                try{
                    DownloadManager dm = (DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
                    Uri downloadUri = Uri.parse(orignalUrl);
                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle("images" + System.currentTimeMillis())
                            .setMimeType("image/png") // Your file type. You can use this code to download other file types also.
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + "images" + ".jpg");
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Image download started.", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}