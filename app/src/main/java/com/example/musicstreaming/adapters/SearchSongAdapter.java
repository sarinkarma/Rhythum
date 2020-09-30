package com.example.musicstreaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.activities.SongActivity;
import com.example.musicstreaming.models.Song;
import com.example.musicstreaming.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.MyHolder> {

    List<Song> songList;
    Context context;

    public SearchSongAdapter(List<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_songs, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        final Song song = songList.get(position);
        holder.song_name.setText(song.getName());
        holder.song_artist.setText(song.getArtist().getName());
        holder.song_duration.setText(song.getTime());

        Picasso.get()
                .load(Constants.IMAGE_URL + song.getAlbum().getImage())
                .into(holder.song_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongActivity.class);
                intent.putExtra("songs", (Serializable) songList);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(songList.size() < 5){
            return songList.size();
        }else{
            return 5;
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView song_img;
        TextView song_name, song_artist, song_duration;
        RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            song_img = itemView.findViewById(R.id.song_img);
            song_name = itemView.findViewById(R.id.song_name);
            song_duration = itemView.findViewById(R.id.song_duration);
            song_artist = itemView.findViewById(R.id.song_artist);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
