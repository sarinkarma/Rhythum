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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.ArtistAlbumAdapter;
import com.example.musicstreaming.controllers.AlbumController;
import com.example.musicstreaming.controllers.SingleArtistController;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.models.Artist;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.GridLayoutItemDecoration;
import com.example.musicstreaming.views.AlbumView;
import com.example.musicstreaming.views.SingleArtistView;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SingleArtistActivity extends AppCompatActivity implements SingleArtistView, AlbumView {

    private CollapsingToolbarLayoutState state;
    TextView toolbar_head, artist_name;
    ImageView artist_image;
    SingleArtistController singleArtistController;
    AlbumController albumController;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String token, artist_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_artist);

        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);

        Intent intent = getIntent();
        artist_id = intent.getStringExtra("artist_id");

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

        initArtist();
        getAlbums();
    }

    public void initArtist(){
        artist_name = findViewById(R.id.textView);
        artist_image = findViewById(R.id.imageView);

        getArtist();
    }

    public void getArtist(){
        singleArtistController = new SingleArtistController(this, this);
        singleArtistController.getSingleArtist(token, artist_id);
    }

    @Override
    public void artist(Artist artist) {
        if(artist != null){
            artist_name.setText(artist.getName());
            toolbar_head.setText(artist.getName());

            Picasso.get()
                    .load(Constants.IMAGE_URL + artist.getImage())
                    .into(artist_image);
        }
    }

    public void getAlbums() {
        sharedPreferences = this.getSharedPreferences("userPref", Context.MODE_PRIVATE);

        albumController = new AlbumController(this, this);
        albumController.getArtistAlbum(token, artist_id);

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