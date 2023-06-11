package com.example.h071211009_finalmobile.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h071211009_finalmobile.API.ApiConfig;
import com.example.h071211009_finalmobile.API.TvShowAPIService;
import com.example.h071211009_finalmobile.Adapter.TvShowAdapter;
import com.example.h071211009_finalmobile.Model.Tv;
import com.example.h071211009_finalmobile.Model.TvResponse;
import com.example.h071211009_finalmobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView tvAlert;
    private RecyclerView recyclerView;
    private TvShowAdapter tvAdapter;

    public TvShowsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);
        initializeViews(view);
        showLoading();
        Retrofit retrofit = ApiConfig.getRetrofit();
        TvShowAPIService tvShowService = retrofit.create(TvShowAPIService.class);
        Call<TvResponse> call = tvShowService.getAiringTodayTV(ApiConfig.API_KEY);

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    hideLoading();
                    TvResponse tvResponse = response.body();
                    List<Tv> tvShows = tvResponse.getTvShows();
                    setTvShows(tvShows);
                } else {
                    showAlert();
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                showAlert();
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setTvShows(List<Tv> tvShows) {
        tvAdapter = new TvShowAdapter(tvShows);
        recyclerView.setAdapter(tvAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void initializeViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        tvAlert = view.findViewById(R.id.tv_alert);
        recyclerView = view.findViewById(R.id.rv_tv_shows);
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