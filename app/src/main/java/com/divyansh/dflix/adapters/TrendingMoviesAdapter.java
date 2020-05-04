package com.divyansh.dflix.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.divyansh.dflix.R;
import com.divyansh.dflix.models.Result;
import com.divyansh.dflix.models.TrendingMovies;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TrendingMoviesAdapter extends RecyclerView.Adapter<TrendingMoviesAdapter.trendingMovieViewHolder> {

    private static final String TAG = "TrendingMoviesAdapter";
    
    private Context context;
    private List<Result> movies = new ArrayList<>();

    @Inject
    public Glide glide;

    private mOnClickListener listener;

    public interface mOnClickListener {
        void onMovieClick(Result result);
    }

    public TrendingMoviesAdapter(Context context) {
        this.context = context;
        Log.d(TAG, "TrendingMoviesAdapter: adapter is working");
    }

    @NonNull
    @Override
    public TrendingMoviesAdapter.trendingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_movies_recycler_view_item, parent, false);
        return new trendingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingMoviesAdapter.trendingMovieViewHolder holder, int position) {
        Result movie = movies.get(position);
        glide.with(context).load("http://image.tmdb.org/t/p/w342" + movie.getPosterPath()).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Result> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class trendingMovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView posterImage;

        public trendingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.trendingMovieImage);
        }
    }
}
