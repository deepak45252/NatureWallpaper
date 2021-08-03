package com.devofindia.naturewallpaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.devofindia.naturewallpaper.Adapters.FragmentpagerAdapter;
import com.devofindia.naturewallpaper.Catagery.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.onesignal.OneSignal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    ViewPager viewPager;
    SearchView searchView;
    ConnectivityManager connectivityManager;
    ProgressDialog progressDialog;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        tabLayout = findViewById(R.id.cttab);
        viewPager = findViewById(R.id.pagerholder);

        viewPager.setAdapter(new FragmentpagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.cnav);

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("21275a0a-2740-477a-ad4f-3bde4fec56b3");

        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open,R.string.close);  // creat toogle upper toolbar
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white)); //toggle color change

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.shareapp:
                        Intent shareappintent = new Intent(Intent.ACTION_SEND);
                        shareappintent.setType("text/plain");
                        shareappintent.putExtra(Intent.EXTRA_TEXT, "Best Application for Wallpaper Install the app now "+ "https://play.google.com/store/apps/details?id=com.devofindia.rahatindorishairy");
                        startActivity(shareappintent);
                        Toast.makeText(MainActivity.this, "Share the App", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.moreapps:
                        Intent moreintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Dev+of+India"));
                        startActivity(moreintent);
                        Toast.makeText(MainActivity.this, "More App", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.feedback:
                        Intent feedintent = new Intent(Intent.ACTION_SEND);
                        feedintent.setPackage("com.google.android.gm");
                        feedintent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ds3073786@gmail.com"});
                        feedintent.putExtra(Intent.EXTRA_SUBJECT, "Best Wallpaper App");
                        feedintent.putExtra(Intent.EXTRA_TEXT, "Wallpaer app feedback");
                        feedintent.setType("message/rfc822");
                        startActivity(feedintent);
                        Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.liked:
                       Intent intent = new Intent(getApplicationContext(), LikedActivity.class);
                       startActivity(intent);
                        Toast.makeText(MainActivity.this, "Liked Activity", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navhome:
                        Toast.makeText(MainActivity.this, "Home Activity", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.privacy_policy:
                        startActivity(new Intent(MainActivity.this, Privacy_policy.class));
                        Toast.makeText(MainActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Default is Selected", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);

        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setQueryHint("Enter catagery");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}