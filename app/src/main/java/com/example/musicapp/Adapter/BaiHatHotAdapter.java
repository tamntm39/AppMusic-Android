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

public class BaiHatHotAdapter extends RecyclerView.Adapter<BaiHatHotAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> baihatArrayList;

    public  BaiHatHotAdapter(Context context, ArrayList<Baihat> baihatArrayList){
        this.context = context;
        this.baihatArrayList = baihatArrayList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_bai_hat_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = baihatArrayList.get(position);
        holder.txttencasi.setText(baihat.getCaSi());
        holder.txttenbaihat.setText(baihat.getTenBaiHat());
        Picasso.get().load(baihat.getHinhBaiHat()).into(holder.imghinhbaihat);
    }

    @Override
    public int getItemCount() {
        return baihatArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbaihat, txttencasi;
        ImageView imghinhbaihat, imgluotthich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenbaihat = itemView.findViewById(R.id.textviewtitlebaihathot);
            txttencasi = itemView.findViewById(R.id.textviewtencasibaihathot);
            imghinhbaihat = itemView.findViewById(R.id.imageviewbaihathot);
            imgluotthich = itemView.findViewById(R.id.imageviewluotthich);
            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgluotthich.setImageResource(R.drawable.icon_hearted_24_ic8);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpDateLuotThich("1",baihatArrayList.get(getPosition()).getIdBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")){
                                Toast.makeText(context,"Đã thích", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    imgluotthich.setEnabled(false);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", baihatArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
