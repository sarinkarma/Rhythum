package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.AlbumSongAdapter;
import com.example.musicstreaming.controllers.SingleAlbumController;
import com.example.musicstreaming.controllers.SongController;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.models.Song;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.VerticalSpaceItemDecoration;
import com.example.musicstreaming.views.SingleAlbumView;
import com.example.musicstreaming.views.SongView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SingleAlbumActivity extends AppCompatActivity implements SingleAlbumView, SongView {

    ImageView album_image;
    TextView album_name, album_artist, album_year, album_copyright;
    SharedPreferences sharedPreferences;
    SingleAlbumController singleAlbumController;
    SongController songController;
    String token, album_id;
    RecyclerView recyclerView;
    ImageButton action_bar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_album);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        action_bar_back = findViewById(R.id.action_bar_back);

        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        album_id = intent.getStringExtra("album_id");

        initView();
        getSongs();
    }

    public void initView(){
        album_image = findViewById(R.id.album_image);
        album_name = findViewById(R.id.album_name);
        album_year = findViewById(R.id.album_year);
        album_artist = findViewById(R.id.album_artist);
        album_copyright = findViewById(R.id.album_copyright);

        getAlbum();
    }

    public void getAlbum(){
        singleAlbumController = new SingleAlbumController(this, this);
        singleAlbumController.getAlbum(token, album_id);
    }


    public void getSongs() {
        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);

        songController = new SongController(this, this);
        songController.getAlbumSong(token, album_id);

        initSongs();
    }

    private void initSongs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.song_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));
    }

    @Override
    public void album(Album album) {
        if(album != null){
            album_name.setText(album.getName());
            album_year.setText(album.getRelease_date());
            album_copyright.setText(album.getCopyright());
            album_artist.setText(album.getArtist().getName());

            Picasso.get()
                    .load(Constants.IMAGE_URL + album.getImage())
                    .into(album_image);
        }
    }

    @Override
    public void songList(List<Song> songList) {
        AlbumSongAdapter songAdapter = new AlbumSongAdapter(songList, this);
        recyclerView.setAdapter(songAdapter);
    }
}