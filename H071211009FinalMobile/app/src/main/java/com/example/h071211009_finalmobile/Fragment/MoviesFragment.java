package com.example.h071211009_finalmobile.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h071211009_finalmobile.API.ApiConfig;
import com.example.h071211009_finalmobile.API.MovieAPIService;
import com.example.h071211009_finalmobile.Adapter.MovieAdapter;
import com.example.h071211009_finalmobile.Model.Movie;
import com.example.h071211009_finalmobile.Model.MovieResponse;
import com.example.h071211009_finalmobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvAlert;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        initializeViews(view);
        showLoading();
        Retrofit retrofit = ApiConfig.getRetrofit();
        MovieAPIService movieService = retrofit.create(MovieAPIService.class);

        Call<MovieResponse> call = movieService.getNowPlayingMovies(ApiConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    hideLoading();
                    MovieResponse movieResponse = response.body();
                    List<Movie> movies = movieResponse.getMovies();
                    setMovie(movies);
                } else {
                    showAlert();
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                showAlert();
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initializeViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        tvAlert = view.findViewById(R.id.tv_alert);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setMovie(List<Movie> movie) {
        movieAdapter = new MovieAdapter(movie);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvAlert.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tvAlert.setVisibility(View.GONE);
    }

    private void showAlert() {
        tvAlert.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
}