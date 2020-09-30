package com.example.musicstreaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.activities.SingleArtistActivity;
import com.example.musicstreaming.models.Artist;
import com.example.musicstreaming.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyHolder> {

    List<Artist> artistList;
    Context context;

    public ArtistAdapter(List<Artist> artistList, Context context) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_top_artist, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final Artist artist = artistList.get(position);
        holder.artist_name.setText(artist.getName());

        Picasso.get()
                .load(Constants.IMAGE_URL + artist.getImage())
                .into(holder.artist_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleArtistActivity.class);
                intent.putExtra("artist_id", artist.get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView artist_image;
        TextView artist_name;
        LinearLayout linearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            artist_image = itemView.findViewById(R.id.artist_image);
            artist_name = itemView.findViewById(R.id.artist_name);
            linearLayout = itemView.findViewById(R.id.artist_list);
        }
    }
}
