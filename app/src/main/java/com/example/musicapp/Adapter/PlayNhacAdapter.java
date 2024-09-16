package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.Baihat;
import com.example.musicapp.R;

import java.util.ArrayList;

public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> mangbaihat;

    public  PlayNhacAdapter (Context context, ArrayList<Baihat> mangbaihat){
        this.context = context;
        this.mangbaihat = mangbaihat;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_play_bai_hat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = mangbaihat.get(position);
        holder.txtTenCaSi.setText(baihat.getCaSi());
        holder.txtindex.setText(position+1+"");
        holder.txtTenBaiHat.setText(baihat.getTenBaiHat());
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtindex, txtTenBaiHat, txtTenCaSi;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtindex = itemView.findViewById(R.id.textviewplaynhacindex);
                txtTenBaiHat = itemView.findViewById(R.id.textviewplaynhactenbaihat);
                txtTenCaSi = itemView.findViewById(R.id.textviewplaynhactencasi);
            }
        }
}
