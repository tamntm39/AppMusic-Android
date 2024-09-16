package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicapp.Adapter.AlbumAdapter;
import com.example.musicapp.Adapter.DanhSachBaiHatAdapter;
import com.example.musicapp.Model.Album;
import com.example.musicapp.Model.Baihat;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Quangcao;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;
import com.example.musicapp.Service.FirebaseService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatActivity extends AppCompatActivity {
    Quangcao quangcao;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgdanhsachbaihat;
    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachbaihat;
    FloatingActionButton floatingActionButton;
    ArrayList<Baihat> mangbaihat;

    Playlist playlist;

    Album album;

    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataIntent();
        anhxa();
        init();
        if(quangcao != null && !quangcao.getTenBaiHat().equals("")){
            setValueInView(quangcao.getTenBaiHat(), quangcao.getHinhBaiHat());
            GetDataQuangCao(quangcao.getIdQuangCao());

        }
        if(playlist != null && !playlist.getTen().equals("")){
            setValueInView(playlist.getTen(), playlist.getIcon());
            GetDataPlayList(playlist.getIdPlaylist());
        }

        if(album != null && !album.getTenAlbum().equals("")){
            setValueInView(album.getTenAlbum(), album.getHinhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }


    }

    private void GetDataAlbum(String idAlbum) {
        FirebaseService.fetchDataAndMapWithCondition("songs", "idAlbum", idAlbum, Baihat.class, new FirebaseService.FirebaseCallback<Baihat>() {
            @Override
            public void onSuccess(ArrayList<Baihat> result) {
                mangbaihat = result;
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }
            @Override
            public void onError(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void GetDataPlayList(String idplaylist) {
        FirebaseService.fetchDataAndMapWithCondition("songs", "idPlaylist", idplaylist, Baihat.class, new FirebaseService.FirebaseCallback<Baihat>() {
            @Override
            public void onSuccess(ArrayList<Baihat> result) {
                mangbaihat = result;
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }
            @Override
            public void onError(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void GetDataQuangCao(String idquangcao) {
        FirebaseService.fetchDataAndMapWithCondition("songs", "idQuangCao", idquangcao, Baihat.class, new FirebaseService.FirebaseCallback<Baihat>() {
            @Override
            public void onSuccess(ArrayList<Baihat> result) {
                mangbaihat = result;
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }
            @Override
            public void onError(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void setValueInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
//        try {
//            URL url = new URL(hinh);
//            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
//            collapsingToolbarLayout.setBackground(bitmapDrawable);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        Picasso.get().load(hinh).into(imgdanhsachbaihat);
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if(intent!= null){
            if(intent.hasExtra("banner")){
                quangcao = (Quangcao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("album")){
                album = (Album) intent.getSerializableExtra("album");
            }
        }
    }

    private void anhxa() {
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbardanhsach);
        recyclerViewdanhsachbaihat = findViewById(R.id.recylerviewdanhsachbaihat);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        imgdanhsachbaihat = findViewById(R.id.imageviewdanhsachbaihat);
    }

    private  void eventClick(){

        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachbaihatActivity.this, PlayNhacActivity.class);
                intent.putExtra("cacbaihat", mangbaihat);
                startActivity(intent);
            }
        });
    }
}