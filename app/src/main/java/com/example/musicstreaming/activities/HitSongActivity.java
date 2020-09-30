package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.HitSongAdapter;
import com.example.musicstreaming.controllers.SongController;
import com.example.musicstreaming.models.Song;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.VerticalSpaceItemDecoration;
import com.example.musicstreaming.views.SongView;

import java.util.List;

public class HitSongActivity extends AppCompatActivity implements SongView {

    SongController songController;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String token;
    ImageButton action_bar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit_song);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        action_bar_back = findViewById(R.id.action_bar_back);

        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSongs();

    }

    public void getSongs() {
        songController = new SongController(this, this);
        songController.getHitSong(token);

        initSongs();
    }

    private void initSongs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.songs_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));
    }

    @Override
    public void songList(List<Song> songList) {
        HitSongAdapter songAdapter = new HitSongAdapter(songList, this);
        recyclerView.setAdapter(songAdapter);
    }
}