package com.mp3cutter.Biit;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp3cutter.Biit.MOdules.DecimalUtils;
import com.mp3cutter.Biit.Models.SongsModel;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

public class Player extends AppCompatActivity implements OnActionClickedListener {


    ID3v2 id3v2Tag;
    List<Double> MarkTimes;
    MediaPlayer mediaPlayer;
    InteractivePlayerView ipv;
    int index = 0;
    int PauseTime;
    Handler mHandler = new Handler();
    Runnable myRunnable;
    ImageView Control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        MarkTimes = new ArrayList<>();
        Control=(ImageView) findViewById(R.id.control);
//
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(getIntent().getStringExtra("FILE_PATH"));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ipv = (InteractivePlayerView) findViewById(R.id.ipv);
        ipv.setMax(mediaPlayer.getDuration() / 1000);
        ipv.setProgress(0);
        ipv.setOnActionClickedListener(this);


        Bundle bundle = getIntent().getBundleExtra("bundle");
        SongsModel songsModel = (SongsModel) bundle.getSerializable("Data");
        TextView Filename = (TextView) findViewById(R.id.Filename);
        Filename.setText(songsModel.mSongsName);


        Mp3File mp3file = null;
        try {
            mp3file = new Mp3File(getIntent().getStringExtra("FILE_PATH"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

//
        if (mp3file.hasId3v2Tag()) {
            id3v2Tag = mp3file.getId3v2Tag();

            TextView Message = (TextView) findViewById(R.id.Message);
            Message.setText(id3v2Tag.getPublisher());


            String Time = id3v2Tag.getComment();
            Log.d("Message", Time);

            String[] SplitTime = Time.split("abc");
            for (String a : SplitTime
                    ) {
                MarkTimes.add(Double.parseDouble(a));


            }


            try {
                mediaPlayer.start();
                StartNow();
                ipv.start();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        final ImageView control = (ImageView) findViewById(R.id.control);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mHandler.removeCallbacks(myRunnable);
                    ipv.stop();
                } else {
                    if (index < MarkTimes.size() - 1)
                        index++;
                    mediaPlayer.seekTo(PauseTime);
                    mediaPlayer.start();
                    ipv.start();
                    StartNow();
                }

            }
        });

        findViewById(R.id.Previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(myRunnable);
                mediaPlayer.pause();
                if (index != 0)
                    index--;
                ipv.setProgress((int) DecimalUtils.round(MarkTimes.get(index),0));
                Double Time = MarkTimes.get(index);
                Time=((Time) * 1000);
                mediaPlayer.seekTo((int) DecimalUtils.round(Time,0));
                mediaPlayer.start();
                ipv.start();
                StartNow();
            }
        });
        findViewById(R.id.Next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(myRunnable);
                mediaPlayer.pause();
                if (index < MarkTimes.size() - 1)
                    index++;
                ipv.setProgress((int) DecimalUtils.round(MarkTimes.get(index),0));
                Double Time = MarkTimes.get(index);
                Time=((Time) * 1000);
                mediaPlayer.seekTo((int) DecimalUtils.round(Time,0));
                mediaPlayer.start();
                ipv.start();
                StartNow();
            }
        });

    }

    @Override
    public void onActionClicked(int id) {
        switch (id) {
            case 1:
                //Called when 1. action is clicked.
                break;
            case 2:
                //Called when 2. action is clicked.
                break;
            case 3:
                //Called when 3. action is clicked.
                break;
            default:
                break;
        }
    }

    public void StartNow() {


        myRunnable = new Runnable() {
            public void run() {

                Double Time = MarkTimes.get(index);
                Time=((Time) * 1000);
                if (Time <= mediaPlayer.getCurrentPosition() && index != MarkTimes.size() - 1) {
                    mediaPlayer.pause();
                    ipv.stop();
                    ipv.setProgress((int) TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()));
                }

                PauseTime = mediaPlayer.getCurrentPosition();
                StartNow();

                if (mediaPlayer.isPlaying())
                {
                    Control.setImageResource(R.drawable.pause);
                }else {
                    Control.setImageResource(R.drawable.play);

                }
            }
        };
        mHandler.postDelayed(myRunnable, 1);


    }


    @Override
    protected void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            ipv.stop();
        }
        super.onPause();
    }
}

