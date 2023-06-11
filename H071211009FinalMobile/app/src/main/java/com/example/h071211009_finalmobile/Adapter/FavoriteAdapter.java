package com.example.h071211009_finalmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211009_finalmobile.Activity.ContentDetailActivity;
import com.example.h071211009_finalmobile.Model.Favorite;
import com.example.h071211009_finalmobile.R;

import java.util.ArrayList;
import java.util.List;

//Adapter berfungsi sebagai perantara antara data dan antarmuka pengguna untuk menampilkan dan mengelola data dalam komponen seperti ListView atau RecyclerView.
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private final ArrayList<Favorite> favorite = new ArrayList<>();
    private List<Favorite> favorites;

    public FavoriteAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Favorite favorite = favorites.get(position);
        holder.setData(favorite, context);
    }

    @Override
    public int getItemCount() {
        return favorites != null ? favorites.size() : 0;
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView, ivType;
        private TextView titleTextView, yearTextView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            titleTextView = itemView.findViewById(R.id.tv_title);
            yearTextView = itemView.findViewById(R.id.tv_year);
            ivType = itemView.findViewById(R.id.iv_type);

        }

        public void setData(Favorite favorite, Context context) {
            String title = favorite.getTitle();
            String year = favorite.getReleaseDate();
            String poster = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getPosterPath();

            titleTextView.setText(title);
            yearTextView.setText(year);
            ivType.setImageResource(R.drawable.star);
            Glide.with(context)
                    .load(poster)
                    .into(posterImageView);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ContentDetailActivity.class);
                    intent.putExtra("favorite", favorite);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}