package com.example.h071211009_finalmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.h071211009_finalmobile.Fragment.FavoritesFragment;
import com.example.h071211009_finalmobile.Fragment.MoviesFragment;
import com.example.h071211009_finalmobile.Fragment.TvShowsFragment;
import com.example.h071211009_finalmobile.R;

public class MainActivity extends AppCompatActivity {

    MoviesFragment moviesFragment = new MoviesFragment();
    TvShowsFragment tvShowsFragment = new TvShowsFragment();
    FavoritesFragment favoritesFragment = new FavoritesFragment();

    ImageButton movie_ib, series_ib, fav_ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesFragment = new MoviesFragment();
        tvShowsFragment = new TvShowsFragment();
        favoritesFragment = new FavoritesFragment();

        movie_ib = findViewById(R.id.movie_ib);
        series_ib = findViewById(R.id.series_ib);
        fav_ib = findViewById(R.id.fav_ib);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MoviesFragment.class.getSimpleName());
        if (!(fragment instanceof MoviesFragment)) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, moviesFragment, MoviesFragment.class.getSimpleName())
                    .commit();
        }

        movie_ib.setOnClickListener(v -> {
            Bundle bundle = getIntent().getExtras();
            moviesFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, moviesFragment).commit();
        });

        series_ib.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, tvShowsFragment).commit();
        });

        fav_ib.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, favoritesFragment).commit();
        });

    }
}