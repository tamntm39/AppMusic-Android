package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaiHatHotAdapter;
import com.example.musicapp.Model.Baihat;
import com.example.musicapp.Model.Quangcao;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;
import com.example.musicapp.Service.FirebaseService;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Bai_Hat_Hot extends Fragment {
    View view;
    RecyclerView recyclerViewBaiHatHot;
    BaiHatHotAdapter baiHatHotAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bai_hat_hot, container, false);
        recyclerViewBaiHatHot = view.findViewById(R.id.recyclerviewbaihathot);
        GetData();
        return view;
    }
    private void LoadDataToView(ArrayList<Baihat> baihatArrayList)
    {
        baiHatHotAdapter = new BaiHatHotAdapter(getActivity(), baihatArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewBaiHatHot.setLayoutManager(linearLayoutManager);
        recyclerViewBaiHatHot.setAdapter(baiHatHotAdapter);
    }

    private void GetData() {
        FirebaseService.fetchDataAndMap("songs", Baihat.class, new FirebaseService.FirebaseCallback<Baihat>() {
            @Override
            public void onSuccess(ArrayList<Baihat> result) {
                LoadDataToView(result);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                // Handle errors
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }
}
