package com.divyansh.dflix.ui.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.divyansh.dflix.BaseActivity;
import com.divyansh.dflix.R;
import com.divyansh.dflix.adapters.TrendingAdapter;
import com.divyansh.dflix.models.Genre;
import com.divyansh.dflix.models.Result;
import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.models.detailMovie.DetailMovie;
import com.divyansh.dflix.models.detailTv.DetailTv;
import com.divyansh.dflix.ui.Resource;
import com.divyansh.dflix.utils.Constants;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class DetailMovieActivity extends BaseActivity implements TrendingAdapter.mOnClickListener{

    private static final String TAG = "DetailMovieActivity";
    private DetailMovieViewModel viewModel;
    private TextView title, date, length, rating, description, placeholder, genres;
    private ImageView poster;
    private Button markAsFavorite;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    public RequestManager glide;

    @Inject
    TrendingAdapter adapter;

    private int id;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getStringExtra("type");
        viewModel = new ViewModelProvider(this, providerFactory).get(DetailMovieViewModel.class);
        Log.d(TAG, "onCreate: movieId " + id);
        Log.d(TAG, "onCreate: type " + type);

        initViews();

        if (type.equals(Constants.TYPE_MOVIE)) {
            subscribeObserversMovie();
            subscribeSimilarMovies();
        } else if (type.equals(Constants.TYPE_TV)) {
            subscribeObserversTv();
            subscribeSimilarTv();
        }
    }

    private void subscribeObserversMovie() {
        viewModel.observeMovieDetails(id).observe(this, new Observer<Resource<DetailMovie>>() {
            @Override
            public void onChanged(Resource<DetailMovie> detailMovieResource) {
                if (detailMovieResource != null) {
                    switch (detailMovieResource.status) {

                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING ...");
                            break;

                        case SUCCESS:
                            Log.d(TAG, "onChanged: Success " + detailMovieResource.data.getTitle());
                            setViewsMovies(detailMovieResource.data);
                            break;

                        case ERROR:
                            Log.d(TAG, "onChanged: ERROR " + detailMovieResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void subscribeSimilarMovies() {
        viewModel.observeSimilarMovies(id).observe(this, new Observer<Resource<TrendingMovies>>() {
            @Override
            public void onChanged(Resource<TrendingMovies> trendingMoviesResource) {
                if (trendingMoviesResource != null) {
                    switch (trendingMoviesResource.status) {
                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING ...");
                            break;

                        case SUCCESS:
                            Log.d(TAG, "onChanged: Success " + trendingMoviesResource.data.getTotalResults());
                            adapter.setMovies(trendingMoviesResource.data.getResults());
                            adapter.setMovieClickListener(DetailMovieActivity.this);
                            break;

                        case ERROR:
                            Log.d(TAG, "onChanged: ERROR " + trendingMoviesResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void subscribeObserversTv() {
        viewModel.observeTvDetails(id).observe(this, new Observer<Resource<DetailTv>>() {
            @Override
            public void onChanged(Resource<DetailTv> detailTvResource) {
                if (detailTvResource != null) {
                    switch (detailTvResource.status) {

                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING ...");
                            break;

                        case SUCCESS:
                            Log.d(TAG, "onChanged: Success " + detailTvResource.data.getOriginalName());
                            setViewsTv(detailTvResource.data);
                            break;

                        case ERROR:
                            Log.d(TAG, "onChanged: ERROR " + detailTvResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void subscribeSimilarTv() {
        viewModel.observeSimilarTVShows(id).observe(this, new Observer<Resource<TrendingMovies>>() {
            @Override
            public void onChanged(Resource<TrendingMovies> trendingMoviesResource) {
                if (trendingMoviesResource != null) {
                    switch (trendingMoviesResource.status) {
                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING ...");
                            break;

                        case SUCCESS:
                            Log.d(TAG, "onChanged: Success " + trendingMoviesResource.data.getTotalResults());
                            adapter.setMovies(trendingMoviesResource.data.getResults());
                            adapter.setMovieClickListener(DetailMovieActivity.this);
                            break;

                        case ERROR:
                            Log.d(TAG, "onChanged: ERROR " + trendingMoviesResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void initViews() {
        poster = findViewById(R.id.poster);
        title = findViewById(R.id.name);
        placeholder = findViewById(R.id.placeholder);
        length = findViewById(R.id.length);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        genres = findViewById(R.id.genres);
        markAsFavorite = findViewById(R.id.mark_as_favorite);
        recyclerView = findViewById(R.id.recycler_view_similar_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setViewsMovies(final DetailMovie movie) {
        glide.load("http://image.tmdb.org/t/p/w342" + movie.getPosterPath()).into(poster);
        title.setText(movie.getOriginalTitle());
        placeholder.setText(movie.getTagline());
        date.setText(movie.getReleaseDate());
        length.setText(movie.getRuntime().toString() + "min");
        rating.setText("IMDB " + movie.getVoteAverage().toString() + "/10");
        description.setText(movie.getOverview());
        for (Genre g : movie.getGenres()) {
            genres.append(g.getName() + " ");
        }
        markAsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveItem(movie.getId(), movie.getPosterPath(), Constants.TYPE_MOVIE);
            }
        });
    }

    private void setViewsTv(final DetailTv tv) {
        glide.load("http://image.tmdb.org/t/p/w342" + tv.getPosterPath()).into(poster);
        title.setText(tv.getName());
        placeholder.setText("Last Aired on " +  tv.getLastAirDate().toString());
        date.setText(tv.getFirstAirDate());
        length.setText(tv.getOriginalName());
        rating.setText("IMDB " + tv.getVoteAverage().toString() + "/10");
        description.setText(tv.getOverview());
        for (Genre g: tv.getGenres()) {
            genres.append(g.getName() + " ");
        }

        markAsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveItem(tv.getId(), tv.getPosterPath(), Constants.TYPE_TV);
            }
        });
    }

    @Override
    public void onResultClick(Result result) {
//        Intent intent = new Intent(this, DetailMovieActivity.class);
//        intent.putExtra("id", result.getId());
//        intent.putExtra("type", type);
//        startActivity(intent);
        finish();
        Intent intent = new Intent(getIntent());
        intent.putExtra("id", result.getId());
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
