package com.divyansh.dflix.ui.main.favorites;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class FavoriteViewModel extends ViewModel {

    private static final String TAG = "FavoriteViewModel";
    
    @Inject
    public FavoriteViewModel() {
        Log.d(TAG, "FavoriteViewModel: working");   
    }
}
