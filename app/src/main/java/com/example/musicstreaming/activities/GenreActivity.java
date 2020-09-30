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
import com.example.musicstreaming.adapters.GenreAdapter;
import com.example.musicstreaming.controllers.AlbumController;
import com.example.musicstreaming.controllers.GenreController;
import com.example.musicstreaming.models.Genre;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.GridLayoutItemDecoration;
import com.example.musicstreaming.views.GenreView;

import java.util.List;

public class GenreActivity extends AppCompatActivity implements GenreView {

    GenreController genreController;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String token;
    ImageButton action_bar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        action_bar_back = findViewById(R.id.action_bar_back);

        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getGenres();
    }

    public void getGenres() {
        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);

        genreController = new GenreController(this, this);
        genreController.getGenre(token);

        initGenre();
    }

    private void initGenre() {
        int spacing = 70;
        boolean includeEdge = true;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.genre_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new GridLayoutItemDecoration(2, spacing, includeEdge));
    }

    @Override
    public void genreList(List<Genre> genreList) {
        GenreAdapter genreAdapter = new GenreAdapter(genreList, this);
        recyclerView.setAdapter(genreAdapter);
    }
}