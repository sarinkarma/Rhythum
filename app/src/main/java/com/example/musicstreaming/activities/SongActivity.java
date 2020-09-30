package com.example.musicstreaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicstreaming.R;
import com.example.musicstreaming.controllers.SingleSongController;
import com.example.musicstreaming.models.Song;
import com.example.musicstreaming.utils.Constants;
//import com.example.musicstreaming.utils.MediaPlayerService;
import com.example.musicstreaming.views.SingleSongView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class SongActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    ImageView image_back, album_image, previous_btn, next_btn, pause_btn, play_btn;
    ImageButton action_bar_back;
    TextView song_name, artist_name, song_current_time, song_time;
    SeekBar song_seek;
    LinearLayout linearLayout;
    SingleSongController singleSongController;
    SharedPreferences sharedPreferences;
    String token;
    String song_id;
    String url;
    int position;
    MediaPlayer mediaPlayer;
    List<Song> songList;
    private Thread playThread, prevThread, nextThread;
    private Handler handler = new Handler();
//    private MediaPlayerService player;
    boolean serviceBound = false;
    private AdView mAdView;
    RewardedAd rewardedAd;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        action_bar_back = findViewById(R.id.action_bar_back);

        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAdView = findViewById(R.id.adView);

        MobileAds.initialize(this, "ca-app-pub-6300812057092644~1081367019");
        AdRequest request = new AdRequest.Builder().build();
        mAdView.loadAd(request);

        loadAd(view);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        int count = sharedPreferences.getInt(String.valueOf(Constants.count), 0);
        Log.d("count", "onCreate: "+ count);

        Intent intent = getIntent();
        songList = (List<Song>) intent.getSerializableExtra("songs");

        position = intent.getIntExtra("position", -1);

        initView();
    }

    public void initView(){
        image_back = findViewById(R.id.imageView2);
        album_image = findViewById(R.id.imageView3);
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.artist_name);
        song_seek = findViewById(R.id.song_seek);
        song_current_time = findViewById(R.id.song_current_time);
        song_time = findViewById(R.id.song_time);
        previous_btn = findViewById(R.id.previous_btn);
        next_btn = findViewById(R.id.next_btn);
        pause_btn = findViewById(R.id.pause_btn);
        play_btn = findViewById(R.id.play_btn);
        linearLayout = findViewById(R.id.layout1);

        getSong();

        song_seek.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                song_current_time.setText(milliSecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                song_seek.setSecondaryProgress(percent);
            }
        });

        mediaPlayer.setOnCompletionListener(this);

    }

    public void getSong(){
        song_seek.setMax(100);
        Song song = songList.get(position);
        Picasso.get()
                .load(Constants.IMAGE_URL + song.getAlbum().getImage())
                .transform(new BlurTransformation(this, 15, 1))
                .into(image_back);

        Picasso.get()
                .load(Constants.IMAGE_URL + song.getAlbum().getImage())
                .into(album_image);

        song_name.setText(song.getName());
        artist_name.setText(song.getArtist().getName());
        url = songList.get(position).getFile();

        prepareMediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
        pause_btn.setImageResource(R.drawable.ic_pause);
        updateSeekBar();
    }

    private void prepareMediaPlayer() {
        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(Constants.SONG_URL + url);
            mediaPlayer.prepare();
            song_time.setText(milliSecondToTimer(mediaPlayer.getDuration()));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
                song_seek.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
                handler.postDelayed(updater, 1000);

            long currentDuration = mediaPlayer.getCurrentPosition();
            song_current_time.setText(milliSecondToTimer(currentDuration));
        }
    };

    private void updateSeekBar() {
        handler.postDelayed(updater, 100);
    }

    private String milliSecondToTimer(long milliSeconds){
        String timerString = "";
        String secondsString;

        int hours = (int)(milliSeconds / (1000 * 60 * 60));
        int minutes = (int)(milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if(hours > 0) {
            timerString = hours + ":";
        }
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = ""+seconds;
        }

        timerString = timerString + minutes + ":" + secondsString;
        return  timerString;
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                previous_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        position = ((position - 1) < 0 ? (songList.size() - 1) : (position - 1));
                        getSong();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playNext();
                    }
                });
            }
        };
        nextThread.start();
    }

    public void playNext(){
        mediaPlayer.stop();
        mediaPlayer.release();
        position = ((position + 1) % songList.size());
        getSong();
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                pause_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mediaPlayer.isPlaying()){
                            handler.removeCallbacks(updater);
                            mediaPlayer.pause();
                            pause_btn.setImageResource(R.drawable.ic_play_button);
                        }else{
                            mediaPlayer.start();
                            pause_btn.setImageResource(R.drawable.ic_pause);
                            updateSeekBar();
                        }
                    }
                });
            }
        };
        playThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        handler.removeCallbacks(updater);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        int count = sharedPreferences.getInt(String.valueOf(Constants.count), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(Constants.count), count+1);
        editor.commit();

        if(count >= 5){
            showAd(view);
        }else{
            playNext();
        }
    }

    public void loadAd(View view){
        this.rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    public void showAd(View view){
        if(this.rewardedAd.isLoaded()){
            RewardedAdCallback callback = new RewardedAdCallback(){
                @Override
                public void onRewardedAdOpened() {
                    super.onRewardedAdOpened();
                }

                @Override
                public void onRewardedAdClosed() {
                    rewardedAd = createAndLoadRewardedAd();
                    playNext();
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(String.valueOf(Constants.count), 0);
                    editor.commit();
                }

                @Override
                public void onRewardedAdFailedToShow(int i) {
                    super.onRewardedAdFailedToShow(i);
                }
            };
            this.rewardedAd.show(this, callback);
        }else{
            loadAd(view);
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }

    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
}