package com.divyansh.dflix.ui.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.divyansh.dflix.R;
import com.divyansh.dflix.adapters.TrendingMoviesAdapter;
import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.network.HomeApi;
import com.divyansh.dflix.ui.main.Resource;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    TrendingMoviesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, providerFactory).get(HomeViewModel.class);
        subscribeObservers();

        recyclerView = view.findViewById(R.id.recycler_view_movies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
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
                            break;
                        }

                        case SUCCESS:{
                            Log.d(TAG, "onChanged: got movies...");
                            Log.d(TAG, "onChanged: total results " + trendingMoviesResource.data.getTotalResults().toString());
                            adapter.setMovies(trendingMoviesResource.data.getResults());
                            break;
                        }

                        case ERROR:{
                            Log.e(TAG, "onChanged: ERROR..." + trendingMoviesResource.message );
                            break;
                        }
                    }
                }
            }
        });
    }
}
