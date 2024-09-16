package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.DanhsachbaihatActivity;
import com.example.musicapp.Adapter.DanhSachBaiHatAdapter;
import com.example.musicapp.Adapter.SearchBaiHatAdapter;
import com.example.musicapp.Model.Baihat;
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

public class Fragment_Tim_Kiem extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewSeach;
    TextView txtNoData;
    SearchBaiHatAdapter searchBaiHatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        toolbar = view.findViewById(R.id.toolbarsearchbaihat);
        recyclerViewSeach = view.findViewById(R.id.recyclerviewsearchbaihat);
        txtNoData = view.findViewById(R.id.txtKhongcoDulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTuKhoaBaiHat(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private  void  SearchTuKhoaBaiHat(String query){
        FirebaseService.fetchDataAndMapWithConditionLike("songs", "tenBaiHat", query, Baihat.class, new FirebaseService.FirebaseCallback<Baihat>() {
            @Override
            public void onSuccess(ArrayList<Baihat> result) {
                ArrayList<Baihat> mangbaihat = result;
                if(mangbaihat.size()>0){
                    searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(), mangbaihat);
                    LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
                    recyclerViewSeach.setLayoutManager(linearLayoutManager);
                    recyclerViewSeach.setAdapter(searchBaiHatAdapter);
                    txtNoData.setVisibility(View.GONE);
                    recyclerViewSeach.setVisibility(View.VISIBLE);

                }
                else {
                    recyclerViewSeach.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onError(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });



//        Dataservice dataservice = APIService.getService();
//        Call<List<Baihat>> callback = dataservice.GetSearchBaiHat(query);
//        callback.enqueue(new Callback<List<Baihat>>() {
//            @Override
//            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
//                ArrayList<Baihat> mangbaihat = (ArrayList<Baihat>) response.body();
//                if(mangbaihat.size()>0){
//                    searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(), mangbaihat);
//                    LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
//                    recyclerViewSeach.setLayoutManager(linearLayoutManager);
//                    recyclerViewSeach.setAdapter(searchBaiHatAdapter);
//                    txtNoData.setVisibility(View.GONE);
//                    recyclerViewSeach.setVisibility(View.VISIBLE);
//
//                }
//                else {
//                    recyclerViewSeach.setVisibility(View.GONE);
//                    txtNoData.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Baihat>> call, Throwable t) {
//
//            }
//        });
    }
}
