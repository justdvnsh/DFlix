package com.divyansh.dflix.ui.main.home;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.network.HomeApi;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private HomeApi homeApi;
    
    @Inject
    public HomeViewModel(HomeApi homeApi) {
        this.homeApi = homeApi;
        Log.d(TAG, "HomeViewModel: view model is created !");
    }
}
