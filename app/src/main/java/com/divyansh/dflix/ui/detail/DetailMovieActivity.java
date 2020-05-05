package com.divyansh.dflix.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.divyansh.dflix.BaseActivity;
import com.divyansh.dflix.R;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class DetailMovieActivity extends BaseActivity {

    private static final String TAG = "DetailMovieActivity";
    private DetailMovieViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movieId = getIntent().getIntExtra("movieId", 0);
        viewModel = new ViewModelProvider(this, providerFactory).get(DetailMovieViewModel.class);
        Log.d(TAG, "onCreate: movieId " + movieId);
    }
}
