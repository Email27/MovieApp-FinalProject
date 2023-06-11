package com.example.h071211009_finalmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.h071211009_finalmobile.Database.DatabaseHelper;
import com.example.h071211009_finalmobile.Model.Favorite;
import com.example.h071211009_finalmobile.Model.Movie;
import com.example.h071211009_finalmobile.Model.Tv;
import com.example.h071211009_finalmobile.R;

public class ContentDetailActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ImageView backdropImageView, backButton, favoriteButton, posterImageView, ivType;
    private TextView titleTextView, ratingTextView, synopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        backdropImageView = findViewById(R.id.iv_backdrop);
        backButton = findViewById(R.id.btn_back);
        favoriteButton = findViewById(R.id.btn_favorite);
        posterImageView = findViewById(R.id.iv_poster);
        ivType = findViewById(R.id.iv_type);
        titleTextView = findViewById(R.id.tv_title);
        ratingTextView = findViewById(R.id.tv_rating);
        synopsisTextView = findViewById(R.id.tv_synopsis);
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null) {
            Movie movie = intent.getParcelableExtra("movie");
            handleFilmDetails(movie.getTitle(), movie.getVoteAverage().toString(), movie.getOverview(), movie.getPosterPath(), movie.getBackdropUrl(), movie.getId(), movie.getReleaseDate(), "movie");
        } else if (intent.getParcelableExtra("show") != null) {
            Tv show = intent.getParcelableExtra("show");
            handleFilmDetails(show.getName(), show.getVoteAverage().toString(), show.getOverview(), show.getPosterUrl(), show.getBackdropUrl(), show.getId(), show.getName(), "tvshow");
        } else if (intent.getParcelableExtra("favorite") != null) {
            Favorite favorite = intent.getParcelableExtra("favorite");
            handleFilmDetails(favorite.getTitle(), favorite.getVoteAverage().toString(), favorite.getOverview(), favorite.getPosterPath(), favorite.getBackdropUrl(), favorite.getId(), favorite.getReleaseDate(), "favorite");
        }

        backButton.setOnClickListener(view -> onBackPressed());

        favoriteButton.setOnClickListener(v -> {
            if (!dbHelper.isMovieInFavorites(titleTextView.getText().toString())) {
                favoriteButton.setImageResource(R.drawable.favorite_filled);
                if (intent.getParcelableExtra("movie") != null) {
                    Movie movie = intent.getParcelableExtra("movie");
                    addMovieToFavorites(movie.getId(), movie.getOverview(), movie.getPosterPath().toString(), movie.getReleaseDate(), movie.getTitle(), movie.getVoteAverage(), movie.getBackdropUrl());
                } else if (intent.getParcelableExtra("show") != null) {
                    Tv show = intent.getParcelableExtra("show");
                    addMovieToFavorites(show.getId(), show.getOverview(), show.getPosterUrl().toString(), show.getFirstAirDate(), show.getName(), show.getVoteAverage(), show.getBackdropUrl());
                } else if (intent.getParcelableExtra("favorite") != null) {
                    Favorite favorite = intent.getParcelableExtra("favorite");
                    addMovieToFavorites(favorite.getId(), favorite.getOverview(), favorite.getPosterPath().toString(), favorite.getReleaseDate(), favorite.getTitle(), favorite.getVoteAverage(), favorite.getBackdropUrl());
                }
            } else {
                favoriteButton.setImageResource(R.drawable.favorite);
                deleteMovieFromFavorites();
            }
        });
    }

    private void addMovieToFavorites(int id, String overview, String posterUrl, String releaseDate, String title, double voteAverage, String backdropUrl) {
        Movie movie = new Movie(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
        long result = dbHelper.insertMovie(movie);
        if (result != -1) {
            Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add movie", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMovieFromFavorites() {
        String title = titleTextView.getText().toString();
        long result = dbHelper.deleteMovie(title);
        if (result != -1) {
            Toast.makeText(this, "Movie deleted from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFilmDetails(String title, String voteAverage, String overview, String posterPath, String backdropPath, int id, String releaseDate, String type) {
        String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + posterPath;
        String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + backdropPath;

        titleTextView.setText(title);
        ratingTextView.setText(voteAverage);
        Glide.with(this).load(posterUrl).into(posterImageView);
        Glide.with(this).load(backdropUrl).into(backdropImageView);
        synopsisTextView.setText(overview);

        if (type.equals("movie")) {
            ivType.setImageResource(R.drawable.movie);
        } else if (type.equals("tvshow")) {
            ivType.setImageResource(R.drawable.tv);
        } else {
            ivType.setImageResource(R.drawable.star);
        }
    }
}