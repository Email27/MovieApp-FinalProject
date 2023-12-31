package com.example.h071211009_finalmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211009_finalmobile.Activity.ContentDetailActivity;
import com.example.h071211009_finalmobile.Model.Movie;
import com.example.h071211009_finalmobile.R;

import java.util.ArrayList;
import java.util.List;

//Adapter berfungsi sebagai perantara antara data dan antarmuka pengguna untuk menampilkan dan mengelola data dalam komponen seperti ListView atau RecyclerView.
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Movie movie = movies.get(position);
        holder.setData(movie, context);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView, ivType;
        private TextView titleTextView, yearTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            titleTextView = itemView.findViewById(R.id.tv_title);
            yearTextView = itemView.findViewById(R.id.tv_year);
            ivType = itemView.findViewById(R.id.iv_type);
        }

        public void setData(Movie movie, Context context) {
            String title = movie.getTitle();
            String year = movie.getReleaseDate().substring(0, 4);
            String poster = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getPosterPath();

            titleTextView.setText(title);
            yearTextView.setText(year);

            Glide.with(context)
                    .load(poster)
                    .into(posterImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ContentDetailActivity.class);
                    intent.putExtra("movie", movie);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}