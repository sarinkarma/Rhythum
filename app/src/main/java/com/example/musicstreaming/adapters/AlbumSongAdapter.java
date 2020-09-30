package com.example.musicstreaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.musicstreaming.models.Genre;
import com.example.musicstreaming.models.Song;
import com.example.musicstreaming.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.MyHolder> {

    List<Song> songList;
    Context context;

    public AlbumSongAdapter(List<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_song, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        final Song song = songList.get(position);
        holder.song_name.setText(song.getName());
        holder.song_num.setText(Integer.toString(position+1));
        holder.song_duration.setText(song.getTime());

        List<Genre> genres = song.getAlbum().getGenre();
        ArrayList<String> gen;
        gen = new ArrayList<String>();
        for (Genre g: genres) {
            gen.add(g.getName());
        }
        String genList = String.join(", ", gen);
        holder.song_genre.setText(genList);

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
        return songList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView song_num, song_name, song_genre, song_duration;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            song_num = itemView.findViewById(R.id.song_num);
            song_name = itemView.findViewById(R.id.song_name);
            song_genre = itemView.findViewById(R.id.song_genre);
            song_duration = itemView.findViewById(R.id.song_duration);
        }
    }
}
