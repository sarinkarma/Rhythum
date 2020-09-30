package com.example.musicstreaming.ui.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.adapters.SearchAlbumAdapter;
import com.example.musicstreaming.adapters.SearchArtistAdapter;
import com.example.musicstreaming.adapters.SearchGenreAdapter;
import com.example.musicstreaming.adapters.SearchSongAdapter;
import com.example.musicstreaming.controllers.SearchController;
import com.example.musicstreaming.controllers.SongController;
import com.example.musicstreaming.models.Search;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.utils.HorizontalSpaceItemDecoration;
import com.example.musicstreaming.utils.VerticalSpaceItemDecoration;

public class SearchFragment extends Fragment implements com.example.musicstreaming.views.SearchView {

    View root;
    RecyclerView songsRecyclerView, genresRecyclerView, artistsRecyclerView, albumsRecyclerView;
    SharedPreferences sharedPreferences;
    String token;
    SearchView searchText;
    SearchController searchController;
    TextView song_not_found, artist_not_found, album_not_found, genre_not_found;
    LinearLayout no_search;
    ScrollView search_scroll;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        searchText = root.findViewById(R.id.search_view);
        song_not_found = root.findViewById(R.id.song_not_found);
        artist_not_found = root.findViewById(R.id.artist_not_found);
        album_not_found = root.findViewById(R.id.album_not_found);
        genre_not_found = root.findViewById(R.id.genre_not_found);
        no_search = root.findViewById(R.id.no_search);
        search_scroll = root.findViewById(R.id.search_scroll);

        sharedPreferences = getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);

        searchController = new SearchController(getContext(), this);

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchController.search(token, query);

                initSearch();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }

    public void initSearch(){
        LinearLayoutManager songLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        songsRecyclerView = root.findViewById(R.id.songs_list);
        songsRecyclerView.setLayoutManager(songLayoutManager);
        songsRecyclerView.setNestedScrollingEnabled(false);
        songsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));

        LinearLayoutManager albumsLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        albumsRecyclerView = root.findViewById(R.id.albums_list);
        albumsRecyclerView.setLayoutManager(albumsLayoutManager);
        albumsRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(70));

        LinearLayoutManager artistsLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        artistsRecyclerView = root.findViewById(R.id.artists_list);
        artistsRecyclerView.setLayoutManager(artistsLayoutManager);
        artistsRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(70));

        LinearLayoutManager genresLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        genresRecyclerView = root.findViewById(R.id.genres_list);
        genresRecyclerView.setLayoutManager(genresLayoutManager);
        genresRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(70));
    }

    @Override
    public void search(Search search) {
        no_search.setVisibility(View.GONE);
        search_scroll.setVisibility(View.VISIBLE);

        if(search.getSongs().size() == 0){
            song_not_found.setVisibility(View.VISIBLE);
            songsRecyclerView.setVisibility(View.GONE);
        }else{
            SearchSongAdapter searchSongAdapter = new SearchSongAdapter(search.getSongs(), getContext());
            songsRecyclerView.setAdapter(searchSongAdapter);
        }

        if(search.getAlbums().size() == 0){
            album_not_found.setVisibility(View.VISIBLE);
            albumsRecyclerView.setVisibility(View.GONE);
        }else{
            SearchAlbumAdapter searchAlbumAdapter = new SearchAlbumAdapter(search.getAlbums(), getContext());
            albumsRecyclerView.setAdapter(searchAlbumAdapter);
        }

        if(search.getArtists().size() == 0){
            artist_not_found.setVisibility(View.VISIBLE);
            artistsRecyclerView.setVisibility(View.GONE);
        }else{
            SearchArtistAdapter searchArtistAdapter = new SearchArtistAdapter(search.getArtists(), getContext());
            artistsRecyclerView.setAdapter(searchArtistAdapter);
        }

        if(search.getGenres().size() == 0){
            genre_not_found.setVisibility(View.VISIBLE);
            genresRecyclerView.setVisibility(View.GONE);
        }else{
            SearchGenreAdapter searchGenreAdapter = new SearchGenreAdapter(search.getGenres(), getContext());
            genresRecyclerView.setAdapter(searchGenreAdapter);
        }
    }
}