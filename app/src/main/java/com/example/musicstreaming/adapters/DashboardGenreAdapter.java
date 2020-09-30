package com.example.musicstreaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreaming.R;
import com.example.musicstreaming.activities.SingleGenreActivity;
import com.example.musicstreaming.models.Genre;
import com.example.musicstreaming.utils.Helpers;

import java.util.List;
import java.util.Random;

public class DashboardGenreAdapter extends RecyclerView.Adapter<DashboardGenreAdapter.MyHolder> {

    List<Genre> genreList;
    Context context;

    public DashboardGenreAdapter(List<Genre> genreList, Context context) {
        this.genreList = genreList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_genre, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final Genre genre = genreList.get(position);
        holder.genre_name.setText(genre.getName());

        int i = new Random().nextInt(254);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.parseColor ("#"+ Helpers.mColors[new Random().nextInt(254)]));
        holder.genre_name.setBackground(shape);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleGenreActivity.class);
                intent.putExtra("genre_id", genre.get_id());
                intent.putExtra("genre_name", genre.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView genre_name;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            genre_name = itemView.findViewById(R.id.genre_name);
            cardView = itemView.findViewById(R.id.genre_background);
        }
    }
}
