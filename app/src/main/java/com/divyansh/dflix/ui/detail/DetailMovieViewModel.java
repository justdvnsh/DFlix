package com.divyansh.dflix.ui.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.models.detailMovie.DetailMovie;
import com.divyansh.dflix.models.detailTv.DetailTv;
import com.divyansh.dflix.network.DetailMovieApi;
import com.divyansh.dflix.ui.Resource;
import com.divyansh.dflix.utils.Constants;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DetailMovieViewModel extends ViewModel {

    private static final String TAG = "DetailMovieViewModel";
    private DetailMovieApi api;
    private MediatorLiveData<Resource<DetailMovie>> movieDetails;
    private MediatorLiveData<Resource<DetailTv>> tvDetails;
    
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
}
