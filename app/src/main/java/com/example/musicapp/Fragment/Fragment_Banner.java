package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musicapp.Adapter.BannerAdapter;
import com.example.musicapp.Model.Quangcao;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;
import com.example.musicapp.Service.FirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    int currentItem;
    Runnable runnable;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        anhxa();
//        addNewData();
        GetDataFirebase();
//        GetData();
//        LoadDataToView();
        return view;
    }

    private void anhxa() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }
    private void addNewData()
    {
        HashMap<String, Object> noteHashMap = new HashMap<>();
        noteHashMap.put("idQuangCao", "2");
        noteHashMap.put("hinhAnh", "https:\\/\\/sanhvolala0509.000webhostapp.com\\/hinhanh\\/quangcao\\/xuatphatdiembg.jpg");
        noteHashMap.put("noiDung", "Obito");
        noteHashMap.put("idBaiHat", "2");
        noteHashMap.put("tenBaiHat", "Biên Giới Long Bình");
        noteHashMap.put("hinhBaiHat", "https:\\/\\/sanhvolala0509.000webhostapp.com\\/hinhanh\\/album\\/danhdoi.jpg");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference noteRef = database.getReference("banners");

        String key = noteRef.push().getKey();
        noteHashMap.put("key", key);
        noteRef.child(key).setValue(noteHashMap).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(MainActivity, "", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private void GetDataFirebase()
    {
        FirebaseService.fetchDataAndMap("banners", Quangcao.class, new FirebaseService.FirebaseCallback<Quangcao>() {
            @Override
            public void onSuccess(ArrayList<Quangcao> result) {
                LoadDataToView(result);
            }
            @Override
            public void onError(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }
    private void LoadDataToView(ArrayList<Quangcao> bannerList) {
        bannerAdapter = new BannerAdapter(getActivity(), bannerList);
        viewPager.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(viewPager);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if(currentItem>= viewPager.getAdapter().getCount()){
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4500);
            }
        };
        handler.postDelayed(runnable, 4500);
    };
}
