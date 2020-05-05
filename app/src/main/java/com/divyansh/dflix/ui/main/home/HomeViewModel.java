package com.divyansh.dflix.ui.main.home;

import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.models.Genres;
import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.network.HomeApi;
import com.divyansh.dflix.ui.main.Resource;
import com.divyansh.dflix.utils.Constants;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private HomeApi homeApi;
    private MediatorLiveData<Resource<TrendingMovies>> movies;
    private MediatorLiveData<Resource<TrendingMovies>> tvShows;
    private MediatorLiveData<Resource<Genres>> genres;

    @Inject
    public HomeViewModel(HomeApi homeApi) {
        this.homeApi = homeApi;
        Log.d(TAG, "HomeViewModel: view model is created !");
    }

    public LiveData<Resource<TrendingMovies>> observeTrendingMovies(){
        if (movies == null) {
            movies = new MediatorLiveData<>();
            movies.setValue(Resource.loading((TrendingMovies) null));

            final LiveData<Resource<TrendingMovies>> source = LiveDataReactiveStreams.fromPublisher(
                    homeApi.getTrendingMovies(Constants.API_KEY)
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

    public LiveData<Resource<TrendingMovies>> observeTrendingTVShows(){
        if (tvShows == null) {
            tvShows = new MediatorLiveData<>();
            tvShows.setValue(Resource.loading((TrendingMovies) null));

            final LiveData<Resource<TrendingMovies>> source = LiveDataReactiveStreams.fromPublisher(
                    homeApi.getTrendingTVShows(Constants.API_KEY)
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


    public LiveData<Resource<Genres>> observeGenres() {
        if (genres == null) {
            genres = new MediatorLiveData<>();
            genres.setValue(Resource.loading((Genres) null));

            final LiveData<Resource<Genres>> source = LiveDataReactiveStreams.fromPublisher(
                    homeApi.getGenres(Constants.API_KEY, Constants.LANGUAGE)
                    .onErrorReturn(new Function<Throwable, Genres>() {
                        @Override
                        public Genres apply(Throwable throwable) throws Exception {
                            Genres genres = new Genres();
                            genres.setId(-1);
                            return genres;
                        }
                    })
                    .map(new Function<Genres, Resource<Genres>>() {
                        @Override
                        public Resource<Genres> apply(Genres genres) throws Exception {
                            if (genres.getId() == -1 ) {
                                return Resource.error("Something Went Wrong", null);
                            }

                            return Resource.success(genres);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );

            genres.addSource(source, new Observer<Resource<Genres>>() {
                @Override
                public void onChanged(Resource<Genres> genresResource) {
                    genres.setValue(genresResource);
                    genres.removeSource(source);
                }
            });
        }

        return genres;
    }
}

