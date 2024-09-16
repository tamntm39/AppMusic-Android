package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicapp.Adapter.AllAlbumAdapter;
import com.example.musicapp.Model.Album;
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

public class DanhSachTatCaAlbumActivity extends AppCompatActivity {

    RecyclerView recyclerViewAlbum;
    Toolbar toolbarAlbum;
    AllAlbumAdapter allAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tat_ca_album);
        init();
        GetData();
    }

    private void GetData() {
        FirebaseService.fetchDataAndMap("albums", Album.class, new FirebaseService.FirebaseCallback<Album>() {
            @Override
            public void onSuccess(ArrayList<Album> result) {
                allAlbumAdapter = new AllAlbumAdapter(DanhSachTatCaAlbumActivity.this, result);
                recyclerViewAlbum.setLayoutManager(new GridLayoutManager(DanhSachTatCaAlbumActivity.this, 2));
                recyclerViewAlbum.setAdapter(allAlbumAdapter);
            }
            @Override
            public void onError(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void init() {
        recyclerViewAlbum= findViewById(R.id.recyclerviewalbum);
        toolbarAlbum= findViewById(R.id.toolbaralbum);
        setSupportActionBar(toolbarAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Albums");
        toolbarAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}