package com.divyansh.dflix.ui.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.divyansh.dflix.BaseActivity;
import com.divyansh.dflix.R;
import com.divyansh.dflix.data.entities.Movies;
import com.divyansh.dflix.models.Genre;
import com.divyansh.dflix.models.detailMovie.DetailMovie;
import com.divyansh.dflix.models.detailTv.DetailTv;
import com.divyansh.dflix.ui.Resource;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class DetailMovieActivity extends BaseActivity {

    private static final String TAG = "DetailMovieActivity";
    private DetailMovieViewModel viewModel;
    private TextView title, date, length, rating, description, placeholder, genres;
    private ImageView poster;
    private Button markAsFavorite;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    public RequestManager glide;

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

        if (type.equals("movie")) {
            subscribeObserversMovie();
        } else if (type.equals("tv")) {
            subscribeObserversTv();
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
                saveMovie(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getVoteAverage(), movie.getPosterPath(), movie.getTagline(), movie.getReleaseDate(), movie.getRuntime(), genres.getText().toString());
            }
        });
    }

    private void saveMovie(Integer id, String title, String overview, Double voteAverage, String posterPath, String tagline, String releaseDate, Integer runtime, String genre) {
        viewModel.saveMovie(id, title, overview, voteAverage, posterPath, tagline, releaseDate, runtime, genre);
    }

    private void setViewsTv(DetailTv tv) {
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
    }
}
