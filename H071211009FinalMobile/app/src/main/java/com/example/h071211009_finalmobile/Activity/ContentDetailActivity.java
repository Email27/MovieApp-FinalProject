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
    private ImageView backdropImageView, backButton, favoriteButton, posterImageView;
    private TextView titleTextView, ratingTextView, synopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        backdropImageView = findViewById(R.id.iv_backdrop);
        backButton = findViewById(R.id.btn_back);
        favoriteButton = findViewById(R.id.btn_favorite);
        posterImageView = findViewById(R.id.iv_poster);
        titleTextView = findViewById(R.id.tv_title);
        ratingTextView = findViewById(R.id.tv_rating);
        synopsisTextView = findViewById(R.id.tv_synopsis);
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null) {
            Movie movie = intent.getParcelableExtra("movie");
            handleFilmDetails(movie.getTitle(), movie.getVoteAverage().toString(), movie.getOverview(), movie.getPosterPath(), movie.getBackdropUrl(), movie.getId(), movie.getReleaseDate());
        } else if (intent.getParcelableExtra("show") != null) {
            Tv show = intent.getParcelableExtra("show");
            handleFilmDetails(show.getName(), show.getVoteAverage().toString(), show.getOverview(), show.getPosterUrl(), show.getBackdropUrl(), show.getId(), show.getName());
        } else if (intent.getParcelableExtra("favorite") != null) {
            Favorite favorite = intent.getParcelableExtra("favorite");
        }

        backButton.setOnClickListener(view -> onBackPressed());

        favoriteButton.setOnClickListener(v -> {
            if (!dbHelper.isMovieInFavorites(titleTextView.getText().toString())) {
                addMovieToFavorites();
            } else {
                deleteMovieFromFavorites();
            }
        });
    }

    private void addMovieToFavorites() {
        int id = getIntent().getIntExtra("id", 0);
        String overview = synopsisTextView.getText().toString();
        String posterUrl = getIntent().getStringExtra("poster_url");
        String releaseDate = getIntent().getStringExtra("release_date");
        String title = titleTextView.getText().toString();
        double voteAverage = Double.parseDouble(ratingTextView.getText().toString());
        String backdropUrl = getIntent().getStringExtra("backdrop_url");

        Movie movie = new Movie(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
        long result = dbHelper.insertMovie(movie);
        if (result != -1) {
            Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMovieFromFavorites() {
        String title = titleTextView.getText().toString();
        long result = dbHelper.deleteMovie(title);
        if (result != -1) {
            Toast.makeText(this, "Movie deleted from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFilmDetails(String title, String voteAverage, String overview, String posterPath, String backdropPath, int id, String releaseDate) {
        String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + posterPath;
        String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + backdropPath;

        titleTextView.setText(title);
        ratingTextView.setText(voteAverage);
        Glide.with(this).load(posterUrl).into(posterImageView);
        Glide.with(this).load(backdropUrl).into(backdropImageView);
        synopsisTextView.setText(overview);
    }
}