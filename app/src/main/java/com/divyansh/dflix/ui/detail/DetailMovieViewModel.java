package com.divyansh.dflix.ui.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.models.detailMovie.DetailMovie;
import com.divyansh.dflix.network.DetailMovieApi;
import com.divyansh.dflix.ui.Resource;

import javax.inject.Inject;

public class DetailMovieViewModel extends ViewModel {

    private static final String TAG = "DetailMovieViewModel";
    private DetailMovieApi api;
    private MediatorLiveData<Resource<DetailMovie>> movieDetails;
    
    @Inject
    public DetailMovieViewModel(DetailMovieApi api) {
        this.api = api;
        Log.d(TAG, "DetailMovieViewModel: view model is working!");
    }
}
