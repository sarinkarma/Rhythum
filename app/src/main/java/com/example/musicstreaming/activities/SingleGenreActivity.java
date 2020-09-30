package com.example.musicstreaming.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.ArtistAlbumAdapter;
import com.example.musicstreaming.controllers.AlbumController;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.GridLayoutItemDecoration;
import com.example.musicstreaming.views.AlbumView;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

public class SingleGenreActivity extends AppCompatActivity implements AlbumView {

    private CollapsingToolbarLayoutState state;
    TextView toolbar_head, genre_name;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    AlbumController albumController;
    String token, genre_id, genre_name_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_genre);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);

        Intent intent = getIntent();
        genre_id = intent.getStringExtra("genre_id");
        genre_name_txt = intent.getStringExtra("genre_name");

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar_head = findViewById(R.id.toolbar_head);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                if (i == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//Modify the status token to expand
                        toolbar.setTitle("");//Set title to EXPANDED
                    }
                } else if (Math.abs(i) >= (appBarLayout.getTotalScrollRange())) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        toolbar.setTitle("");//Set title not to display
                        toolbar_head.setVisibility(View.VISIBLE);
                        state = CollapsingToolbarLayoutState.COLLAPSED;//Modified status marked as folded
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERMEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            toolbar_head.setVisibility(View.GONE);//Hide Play Button When Changed from Folding to Intermediate State
                        }
                        toolbar.setTitle("");
                        state = CollapsingToolbarLayoutState.INTERMEDIATE;//Modify the status tag to the middle
                    }
                }

            }
        });

        initGenre();
    }

    public void initGenre(){
        genre_name = findViewById(R.id.textView);
        genre_name.setText(genre_name_txt);
        toolbar_head.setText(genre_name_txt);

        getAlbums();
    }

    public void getAlbums(){
        albumController = new AlbumController(this, this);
        albumController.getGenreAlbum(token, genre_id);

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
        ArtistAlbumAdapter artistAlbumAdapter = new ArtistAlbumAdapter(albumList, this);
        recyclerView.setAdapter(artistAlbumAdapter);
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERMEDIATE
    }
}