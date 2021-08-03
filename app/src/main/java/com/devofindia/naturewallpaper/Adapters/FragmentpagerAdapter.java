package com.devofindia.naturewallpaper.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.devofindia.naturewallpaper.Catagery.Boy_Fragment;
import com.devofindia.naturewallpaper.Catagery.Car_Fragment;
import com.devofindia.naturewallpaper.Catagery.Nature_Fragment;

public class FragmentpagerAdapter extends FragmentPagerAdapter {

    public FragmentpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Nature_Fragment();
            case 1: return new Boy_Fragment();
            case 2: return new Car_Fragment();
            default: return new Nature_Fragment();
        }
//        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position){
            case 0:
                title="Nature";
                break;
            case 1:
                title = "Boy";
                break;
            case 2:
                title = "Car";
                break;
            default: title = "Nature";
        }
        return title;
    }
}
