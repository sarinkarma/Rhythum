package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.AlbumAdapter;
import com.example.musicstreaming.controllers.AlbumController;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.GridLayoutItemDecoration;
import com.example.musicstreaming.views.AlbumView;

import java.util.List;

public class AlbumActivity extends AppCompatActivity implements AlbumView {

    AlbumController albumController;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String token;
    ImageButton action_bar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);

        action_bar_back = findViewById(R.id.action_bar_back);

        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        getAlbums();

    }

    public void goBack(){
        this.onBackPressed();
    }

    public void getAlbums() {
        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);

        albumController = new AlbumController(this, this);
        albumController.getAlbum(token);

        initAlbum();
    }

    private void initAlbum() {
        int spacing = 70;
        boolean includeEdge = true;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.album_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new GridLayoutItemDecoration(2, spacing, includeEdge));
    }

    @Override
    public void albumList(List<Album> albumList) {
        AlbumAdapter albumAdapter = new AlbumAdapter(albumList, this);
        recyclerView.setAdapter(albumAdapter);
    }
}