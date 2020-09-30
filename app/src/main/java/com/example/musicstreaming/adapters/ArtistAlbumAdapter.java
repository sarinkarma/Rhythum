package com.example.musicstreaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.activities.SingleAlbumActivity;
import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAlbumAdapter extends RecyclerView.Adapter<ArtistAlbumAdapter.MyHolder> {

    List<Album> albumList;
    Context context;

    public ArtistAlbumAdapter(List<Album> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_album, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final Album album = albumList.get(position);
        holder.album_name.setText(album.getName());
        holder.album_year.setText(album.getRelease_date());

        Picasso.get()
                .load(Constants.IMAGE_URL + album.getImage())
                .into(holder.album_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleAlbumActivity.class);
                intent.putExtra("album_id", album.get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView album_image;
        TextView album_name, album_year;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            album_image = itemView.findViewById(R.id.album_image);
            album_name = itemView.findViewById(R.id.album_name);
            album_year = itemView.findViewById(R.id.album_year);
        }
    }
}
