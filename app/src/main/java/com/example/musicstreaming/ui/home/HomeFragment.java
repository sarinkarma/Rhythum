package com.example.musicstreaming.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.activities.AlbumActivity;
import com.example.musicstreaming.activities.ArtistActivity;
import com.example.musicstreaming.activities.GenreActivity;
import com.example.musicstreaming.activities.HitSongActivity;
import com.example.musicstreaming.adapters.DashboardAlbumAdapter;
import com.example.musicstreaming.adapters.DashboardArtistAdapter;
import com.example.musicstreaming.adapters.DashboardGenreAdapter;
import com.example.musicstreaming.adapters.DashboardSongAdapter;
import com.example.musicstreaming.controllers.AlbumController;
import com.example.musicstreaming.controllers.ArtistController;
import com.example.musicstreaming.controllers.GenreController;
import com.example.musicstreaming.controllers.SongController;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.models.Artist;
import com.example.musicstreaming.models.Genre;
import com.example.musicstreaming.models.Song;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.HorizontalSpaceItemDecoration;
import com.example.musicstreaming.utils.VerticalSpaceItemDecoration;
import com.example.musicstreaming.views.AlbumView;
import com.example.musicstreaming.views.GenreView;
import com.example.musicstreaming.views.GetArtistView;
import com.example.musicstreaming.views.SongView;

import java.util.List;

public class HomeFragment extends Fragment implements GetArtistView, AlbumView, GenreView, SongView {

    View root;
    TextView album_view_more, artist_view_more, hits_view_more, genre_view_more;
    ArtistController artistController;
    AlbumController albumController;
    GenreController genreController;
    SongController songController;
    SharedPreferences sharedPreferences;
    RecyclerView artistRecyclerView;
    RecyclerView albumRecyclerView;
    RecyclerView genreRecyclerView;
    RecyclerView songRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);

        initView(root);
        getAlbum(root);
        getGenre(root);
        getSongs(root);
        getArtist(root);
        return root;
    }

    public void initView(View root){

        album_view_more = root.findViewById(R.id.album_view_more);
        artist_view_more = root.findViewById(R.id.artist_view_more);
        hits_view_more = root.findViewById(R.id.hits_view_more);
        genre_view_more = root.findViewById(R.id.genre_view_more);

        album_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                startActivity(intent);
            }
        });

        artist_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArtistActivity.class);
                startActivity(intent);
            }
        });

        hits_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HitSongActivity.class);
                startActivity(intent);
            }
        });

        genre_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenreActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getArtist(View root) {
        sharedPreferences = this.getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.TOKEN, null);

        artistController = new ArtistController(root.getContext(), this);
        artistController.getArtist(token);

        initArtist(root);
    }

    public void getAlbum(View root) {
        sharedPreferences = this.getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.TOKEN, null);

        albumController = new AlbumController(root.getContext(), this);
        albumController.getAlbum(token);

        initAlbum(root);
    }

    public void getSongs(View root) {
        sharedPreferences = this.getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.TOKEN, null);

        songController = new SongController(root.getContext(), this);
        songController.getHitSong(token);

        initSongs(root);
    }

    public void getGenre(View root) {
        sharedPreferences = this.getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.TOKEN, null);

        genreController = new GenreController(root.getContext(), this);
        genreController.getGenre(token);

        initGenre(root);
    }

    private void initArtist(View root) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        artistRecyclerView = root.findViewById(R.id.artist_recycler_view);
        artistRecyclerView.setLayoutManager(layoutManager);
        artistRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(70));
    }

    private void initAlbum(View root) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        albumRecyclerView = root.findViewById(R.id.latest_album_recycler_view);
        albumRecyclerView.setLayoutManager(layoutManager);
        albumRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(70));
    }

    private void initSongs(View root) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        songRecyclerView = root.findViewById(R.id.hits_recycler_view);
        songRecyclerView.setLayoutManager(layoutManager);
        songRecyclerView.setNestedScrollingEnabled(false);
        songRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));
    }

    private void initGenre(View root) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        genreRecyclerView = root.findViewById(R.id.genre_recycler_view);
        genreRecyclerView.setLayoutManager(layoutManager);
        genreRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(70));
    }

    @Override
    public void artistList(List<Artist> artistList) {
        DashboardArtistAdapter artistAdapter = new DashboardArtistAdapter(artistList, root.getContext());
        artistRecyclerView.setAdapter(artistAdapter);
    }

    @Override
    public void albumList(List<Album> albumList) {
        DashboardAlbumAdapter albumAdapter = new DashboardAlbumAdapter(albumList, root.getContext());
        albumRecyclerView.setAdapter(albumAdapter);
    }

    @Override
    public void genreList(List<Genre> genreList) {
        DashboardGenreAdapter genreAdapter = new DashboardGenreAdapter(genreList, root.getContext());
        genreRecyclerView.setAdapter(genreAdapter);
    }

    @Override
    public void songList(List<Song> songList) {
        DashboardSongAdapter songAdapter = new DashboardSongAdapter(songList, root.getContext());
        songRecyclerView.setAdapter(songAdapter);
    }
}