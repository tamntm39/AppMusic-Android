package com.example.musicapp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.ViewPagePlaylistNhac;
import com.example.musicapp.Fragment.Fragment_Dia_Nhac;
import com.example.musicapp.Fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.musicapp.Model.Baihat;
import com.example.musicapp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarPlayNhac;
    TextView txttimesong, txttotaltimesong;
    SeekBar sktime;
    ImageButton imgplay, imgshuffle, imgpre, imgnext, imgrepeat;
    ViewPager viewPagerPlayNhac;

    public  static  ArrayList<Baihat> mangbaihat = new ArrayList<>();
    public  static ViewPagePlaylistNhac adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac ;
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;
    MediaPlayer mediaPlayer;

    int position = 0;
    boolean repeat= false;
    boolean checkrandom = false;
    boolean next = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();

        eventClick();



    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapternhac.getItem(1) != null){
                    if(mangbaihat.size()>0){
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    }
                    else {
                        handler.postDelayed(this, 300);
                    }

                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.play_icon);
                }else {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.pause_icon);
                }
            }
        });

        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat == false){
                    if(checkrandom == true){
                        checkrandom = false;
                        imgshuffle.setImageResource(R.drawable.ic8_shuffle_50);

                    }
                    imgrepeat.setImageResource(R.drawable.looped);
                    repeat =true;
                }
                else {
                    imgrepeat.setImageResource(R.drawable.loop_icon);
                    repeat = false;
                }
            }
        });

        imgshuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkrandom == false){
                    if(repeat == true){
                        repeat = false;
                        imgrepeat.setImageResource(R.drawable.loop_icon);
                    }
                    imgshuffle.setImageResource(R.drawable.ic8_shuffled_1);
                     checkrandom=true;
                } else {
                    imgshuffle.setImageResource(R.drawable.ic8_shuffle_50);
                    checkrandom = false;
                }
            }
        });

        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.pause_icon);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(position == index){
                               position += 1;
                            }
                            position = index;
                        }
                        if(position > mangbaihat.size() - 1 ){
                            position = 0;
                        }
                        new PlayMP3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get( position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 1000);
            }
        });

        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.pause_icon);
                        position--;
                        if(position < 0){
                            position = mangbaihat.size()-1;
                        }
                        if(repeat == true){
                            position += 1;
                        }

                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(position == index){
                                position += 1;
                            }
                            position = index;
                        }

                        new PlayMP3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get( position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 1000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if(intent !=null){
            if(intent.hasExtra("cakhuc")){
                Baihat baihat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baihat);
            }
            if(intent.hasExtra("cacbaihat")){
                ArrayList<Baihat> baihatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baihatArrayList;
            }

        }

    }

    private void init() {
        toolbarPlayNhac = findViewById(R.id.toolbarplaynhac);
        txttimesong = findViewById(R.id.textviewtimesong);
        txttotaltimesong = findViewById(R.id.textviewtotaltimesong);
        sktime = findViewById(R.id.seekbarsong);
        imgplay = findViewById(R.id.imagebuttonplay);
        imgshuffle = findViewById(R.id.imagebuttonsuffle);
        imgpre = findViewById(R.id.imagebuttonpre);
        imgnext = findViewById(R.id.imagebuttonnext);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        viewPagerPlayNhac = findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarPlayNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlayNhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();
            }
        });
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_bai_hat = new Fragment_Play_Danh_Sach_Bai_Hat();

        toolbarPlayNhac.setTitleTextColor(Color.WHITE);
        adapternhac = new ViewPagePlaylistNhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_dia_nhac);
        adapternhac.AddFragment(fragment_play_danh_sach_bai_hat);
        viewPagerPlayNhac.setAdapter(adapternhac);
//        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(1);

        if(mangbaihat.size()> 0 ){
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenBaiHat());
            new PlayMP3().execute(mangbaihat.get(0).getLinkBaiHat());
            imgplay.setImageResource(R.drawable.pause_icon);
        }
    }
    class PlayMP3 extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
            mediaPlayer.setDataSource(baihat);
            mediaPlayer.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txttotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTime(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txttimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 100);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }, 100);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true){
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.pause_icon);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(position == index){
                                position += 1;
                            }
                            position = index;
                        }
                        if(position > mangbaihat.size() - 1 ){
                            position = 0;
                        }
                        new PlayMP3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get( position).getTenBaiHat());
                    }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 1000);
                next = false;
                handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this, 500);
                }
            }
        }, 500);
    }

}