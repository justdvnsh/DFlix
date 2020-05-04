package com.divyansh.dflix.ui.main.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

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

}
