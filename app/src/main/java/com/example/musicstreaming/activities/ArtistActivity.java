package com.example.musicstreaming.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.AlbumAdapter;
import com.example.musicstreaming.adapters.ArtistAdapter;
import com.example.musicstreaming.controllers.AlbumController;
import com.example.musicstreaming.controllers.ArtistController;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.models.Artist;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.GridLayoutItemDecoration;
import com.example.musicstreaming.views.AlbumView;
import com.example.musicstreaming.views.GetArtistView;

import java.util.List;

public class ArtistActivity extends AppCompatActivity implements GetArtistView {

    ArtistController artistController;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String token;
    ImageButton action_bar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        action_bar_back = findViewById(R.id.action_bar_back);

        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        getArtists();

    }

    public void goBack(){
        this.onBackPressed();
    }

    public void getArtists() {
        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);

        artistController = new ArtistController(this, this);
        artistController.getArtist(token);

        initArtist();
    }

    private void initArtist() {
        int spacing = 70;
        boolean includeEdge = true;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.artist_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new GridLayoutItemDecoration(2, spacing, includeEdge));
    }

    @Override
    public void artistList(List<Artist> artistList) {
        ArtistAdapter artistAdapter = new ArtistAdapter(artistList, this);
        recyclerView.setAdapter(artistAdapter);
    }
}