package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.musicapp.Adapter.MainViewPagerAdapter;
import com.example.musicapp.Fragment.Fragment_Tim_Kiem;
import com.example.musicapp.Fragment.Fragment_Trang_Chu;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
    }

    public  void init(){
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager.setAdapter(mainViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){

                case 0:
                    tab.setIcon(R.drawable.home_icon);
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setIcon(R.drawable.search_icon);
                    tab.setText("Search");
                    break;
                case 2:
                    tab.setIcon(R.drawable.baseline_account_circle_24);
                    tab.setText("You");
                    break;

            }
        }).attach();
    }

    private void anhxa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }


}