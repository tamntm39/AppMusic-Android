package com.example.musicapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicapp.Fragment.Fragment_Profile;
import com.example.musicapp.Fragment.Fragment_Tim_Kiem;
import com.example.musicapp.Fragment.Fragment_Trang_Chu;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {


    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_Trang_Chu();
            case 1:
                return new Fragment_Tim_Kiem();
            case 2:
                return new Fragment_Profile();

            default:
                return new Fragment_Trang_Chu();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
