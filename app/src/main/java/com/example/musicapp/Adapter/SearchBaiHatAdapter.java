package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.PlayNhacActivity;
import com.example.musicapp.Model.Baihat;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> mangbaihat;

    public SearchBaiHatAdapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.dong_search_bai_hat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = mangbaihat.get(position);
        holder.txtTenBaiHat.setText(baihat.getTenBaiHat());
        holder.txtTenCaSi.setText(baihat.getCaSi());
        Picasso.get().load(baihat.getHinhBaiHat()).into(holder.imgHinhBaiHat);
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenBaiHat, txtTenCaSi;
        ImageView imgLuotThich, imgHinhBaiHat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenBaiHat = itemView.findViewById(R.id.txtSearchTenBaiHat);
            txtTenCaSi = itemView.findViewById(R.id.txtSearchTenCaSi);
            imgHinhBaiHat = itemView.findViewById(R.id.imgSearchHinhBaiHat);
            imgLuotThich = itemView.findViewById(R.id.imageviewSeachLuotThich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imgLuotThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgLuotThich.setImageResource(R.drawable.icon_hearted_24_ic8);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpDateLuotThich("1", mangbaihat.get(getPosition()).getIdBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua  =  response.body();
                            if(ketqua.equals("Success")){
                                Toast.makeText(context  , "Đã thích", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context  , "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imgLuotThich.setEnabled(false);
                }
            });
        }
    }
}
