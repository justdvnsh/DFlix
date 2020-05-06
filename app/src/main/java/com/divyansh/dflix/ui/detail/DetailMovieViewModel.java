package com.divyansh.dflix.ui.detail;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.data.AppDatabase;
import com.divyansh.dflix.data.entities.Saved;
import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.models.detailMovie.DetailMovie;
import com.divyansh.dflix.models.detailTv.DetailTv;
import com.divyansh.dflix.network.DetailMovieApi;
import com.divyansh.dflix.ui.Resource;
import com.divyansh.dflix.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DetailMovieViewModel extends ViewModel {

    private static final String TAG = "DetailMovieViewModel";
    private DetailMovieApi api;
    private MediatorLiveData<Resource<DetailMovie>> movieDetails;
    private MediatorLiveData<Resource<DetailTv>> tvDetails;
    private MediatorLiveData<Resource<TrendingMovies>> movies;
    private MediatorLiveData<Resource<TrendingMovies>> tvShows;

    @Inject
    AppDatabase db;

    @Inject
    public DetailMovieViewModel(DetailMovieApi api) {
        this.api = api;
        Log.d(TAG, "DetailMovieViewModel: view model is working!");
    }

    public LiveData<Resource<DetailMovie>> observeMovieDetails(int id) {

        if (movieDetails == null) {
            movieDetails = new MediatorLiveData<>();
            movieDetails.setValue(Resource.loading((DetailMovie) null));

            final LiveData<Resource<DetailMovie>> source = LiveDataReactiveStreams.fromPublisher(
                    api.getMovieDetails(id, Constants.API_KEY, Constants.LANGUAGE)
                    .onErrorReturn(new Function<Throwable, DetailMovie>() {
                        @Override
                        public DetailMovie apply(Throwable throwable) throws Exception {
                            DetailMovie movie = new DetailMovie();
                            movie.setId(-1);
                            return movie;
                        }
                    })
                    .map(new Function<DetailMovie, Resource<DetailMovie>>() {
                        @Override
                        public Resource<DetailMovie> apply(DetailMovie detailMovie) throws Exception {
                            if (detailMovie.getId() == -1) {
                                return Resource.error("Something Went Wrong", null);
                            }

                            return Resource.success(detailMovie);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );

            movieDetails.addSource(source, new Observer<Resource<DetailMovie>>() {
                @Override
                public void onChanged(Resource<DetailMovie> detailMovieResource) {
                    movieDetails.setValue(detailMovieResource);
                    movieDetails.removeSource(source);
                }
            });
        }

        return movieDetails;

    };

    public LiveData<Resource<DetailTv>> observeTvDetails(int id) {

        if (tvDetails == null) {
            tvDetails = new MediatorLiveData<>();
            tvDetails.setValue(Resource.loading((DetailTv) null));

            final LiveData<Resource<DetailTv>> source = LiveDataReactiveStreams.fromPublisher(
                    api.getTvDetails(id, Constants.API_KEY, Constants.LANGUAGE)
                    .onErrorReturn(new Function<Throwable, DetailTv>() {
                        @Override
                        public DetailTv apply(Throwable throwable) throws Exception {
                            DetailTv tv = new DetailTv();
                            tv.setId(-1);
                            return tv;
                        }
                    })
                    .map(new Function<DetailTv, Resource<DetailTv>>() {
                        @Override
                        public Resource<DetailTv> apply(DetailTv detailTv) throws Exception {
                            if (detailTv.getId() == -1) {
                                return Resource.error("Something Went Wrong", null);
                            }

                            return Resource.success(detailTv);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );

            tvDetails.addSource(source, new Observer<Resource<DetailTv>>() {
                @Override
                public void onChanged(Resource<DetailTv> detailTvResource) {
                    tvDetails.setValue(detailTvResource);
                    tvDetails.removeSource(source);
                }
            });

        }

        return tvDetails;

    }

    public void saveItem(int id, String posterPath, String type) {
        Saved saved = new Saved(id, posterPath, type);
        new insertDbTask(db).execute(saved);
    }

    private static class insertDbTask extends AsyncTask<Saved, Void, Void> {

        private AppDatabase db;

        public insertDbTask(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Saved... saveds) {
            int[] ids = {saveds[0].getId()};
            List<Saved> values = db.savedDao().findById(ids);
            Log.d(TAG, "saveValues: values " + values.size());
            if (values.size() == 0) {
                db.savedDao().save(saveds[0]);
                Log.d(TAG, "doInBackground: Saved ");
            } else {
                Log.d(TAG, "doInBackground: Item already in the database");
            }

            return null;
        }
    }

    public LiveData<Resource<TrendingMovies>> observeSimilarMovies(int id){
        if (movies == null) {
            movies = new MediatorLiveData<>();
            movies.setValue(Resource.loading((TrendingMovies) null));

            final LiveData<Resource<TrendingMovies>> source = LiveDataReactiveStreams.fromPublisher(
                    api.getSimilar(Constants.TYPE_MOVIE, id, Constants.API_KEY, Constants.LANGUAGE, 1)
                            .onErrorReturn(new Function<Throwable, TrendingMovies>() {
                                @Override
                                public TrendingMovies apply(Throwable throwable) throws Exception {
                                    TrendingMovies trendingMovies = new TrendingMovies();
                                    trendingMovies.setId(-1);
                                    return trendingMovies;
                                }
                            })
                            .map(new Function<TrendingMovies, Resource<TrendingMovies>>() {
                                @Override
                                public Resource<TrendingMovies> apply(TrendingMovies trendingMovies) throws Exception {

                                    if (trendingMovies.getId() == -1) {
                                        return Resource.error("Something Went Wrong", null);
                                    }

                                    return Resource.success(trendingMovies);
                                }
                            })
                            .subscribeOn(Schedulers.io())

            );

            movies.addSource(source, new Observer<Resource<TrendingMovies>>() {
                @Override
                public void onChanged(Resource<TrendingMovies> trendingMoviesResource) {
                    movies.setValue(trendingMoviesResource);
                    movies.removeSource(source);
                }
            });
        }
        return movies;
    }

    public LiveData<Resource<TrendingMovies>> observeSimilarTVShows(int id){
        if (tvShows == null) {
            tvShows = new MediatorLiveData<>();
            tvShows.setValue(Resource.loading((TrendingMovies) null));

            final LiveData<Resource<TrendingMovies>> source = LiveDataReactiveStreams.fromPublisher(
                    api.getSimilar(Constants.TYPE_TV, id, Constants.API_KEY, Constants.LANGUAGE, 1)
                            .onErrorReturn(new Function<Throwable, TrendingMovies>() {
                                @Override
                                public TrendingMovies apply(Throwable throwable) throws Exception {
                                    TrendingMovies trendingMovies = new TrendingMovies();
                                    trendingMovies.setId(-1);
                                    return trendingMovies;
                                }
                            })
                            .map(new Function<TrendingMovies, Resource<TrendingMovies>>() {
                                @Override
                                public Resource<TrendingMovies> apply(TrendingMovies trendingMovies) throws Exception {

                                    if (trendingMovies.getId() == -1) {
                                        return Resource.error("Something Went Wrong", null);
                                    }

                                    return Resource.success(trendingMovies);
                                }
                            })
                            .subscribeOn(Schedulers.io())

            );

            tvShows.addSource(source, new Observer<Resource<TrendingMovies>>() {
                @Override
                public void onChanged(Resource<TrendingMovies> trendingMoviesResource) {
                    tvShows.setValue(trendingMoviesResource);
                    tvShows.removeSource(source);
                }
            });
        }
        return tvShows;
    }

}
