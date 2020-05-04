package com.divyansh.dflix.ui.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.divyansh.dflix.R;
import com.divyansh.dflix.adapters.TrendingAdapter;
import com.divyansh.dflix.models.Result;
import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.ui.main.Resource;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment implements TrendingAdapter.mOnClickListener{

    private static final String TAG = "HomeFragment";
    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewTV;
    private ProgressBar movieProgress, tvProgress;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    TrendingAdapter movieAdapter;

    @Inject
    TrendingAdapter tvAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, providerFactory).get(HomeViewModel.class);
        subscribeObservers();
        subscribeObserversTv();

        recyclerView = view.findViewById(R.id.recycler_view_movies);
        recyclerViewTV = view.findViewById(R.id.recycler_view_tv);
        movieProgress = view.findViewById(R.id.movie_progress_bar);
        tvProgress = view.findViewById(R.id.tv_progress_bar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
        LinearLayoutManager linearLayoutManagerTV = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewTV.setHasFixedSize(true);
        recyclerViewTV.setLayoutManager(linearLayoutManagerTV);
        recyclerViewTV.setAdapter(tvAdapter);
    }

    private void subscribeObservers() {
        viewModel.observeTrendingMovies().removeObservers(getViewLifecycleOwner());
        viewModel.observeTrendingMovies().observe(getViewLifecycleOwner(), new Observer<Resource<TrendingMovies>>() {
            @Override
            public void onChanged(Resource<TrendingMovies> trendingMoviesResource) {
                if (trendingMoviesResource != null) {
                    switch (trendingMoviesResource.status) {
                        case LOADING:{
                            Log.d(TAG, "onChanged: LOADING...");
                            showProgress(true, movieProgress);
                            break;
                        }

                        case SUCCESS:{
                            Log.d(TAG, "onChanged: got movies...");
                            Log.d(TAG, "onChanged: total results " + trendingMoviesResource.data.getTotalResults().toString());
                            showProgress(false, movieProgress);
                            movieAdapter.setMovies(trendingMoviesResource.data.getResults());
                            movieAdapter.setMovieClickListener(HomeFragment.this);
                            break;
                        }

                        case ERROR:{
                            Log.e(TAG, "onChanged: ERROR..." + trendingMoviesResource.message );
                            showProgress(false, movieProgress);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void subscribeObserversTv() {
        viewModel.observeTrendingTVShows().removeObservers(getViewLifecycleOwner());
        viewModel.observeTrendingTVShows().observe(getViewLifecycleOwner(), new Observer<Resource<TrendingMovies>>() {
            @Override
            public void onChanged(Resource<TrendingMovies> trendingMoviesResource) {
                if (trendingMoviesResource != null) {
                    switch (trendingMoviesResource.status) {
                        case LOADING:{
                            Log.d(TAG, "onChanged: LOADING...");
                            showProgress(true, tvProgress);
                            break;
                        }

                        case SUCCESS:{
                            Log.d(TAG, "onChanged: got TV SHOWS...");
                            Log.d(TAG, "onChanged: total results " + trendingMoviesResource.data.getTotalResults().toString());
                            showProgress(false, tvProgress);
                            tvAdapter.setMovies(trendingMoviesResource.data.getResults());
                            tvAdapter.setMovieClickListener(HomeFragment.this);
                            break;
                        }

                        case ERROR:{
                            Log.e(TAG, "onChanged: ERROR..." + trendingMoviesResource.message );
                            showProgress(false, tvProgress);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showProgress(boolean b, ProgressBar progressBar) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResultClick(Result result) {
        if (result.getTitle() != null) {
            Toast.makeText(getContext(), "POSTER CLIKCED at " + result.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "POSTER CLIKCED at " + result.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
