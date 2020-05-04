package com.divyansh.dflix.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.divyansh.dflix.R;
import com.divyansh.dflix.models.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.trendingMovieViewHolder> {

    private static final String TAG = "TrendingAdapter";
    
    private Context context;
    private List<Result> result = new ArrayList<>();

    @Inject
    public Glide glide;

    private mOnClickListener listener;

    public interface mOnClickListener {
        void onResultClick(Result result);
    }

    public TrendingAdapter(Context context) {
        this.context = context;
        Log.d(TAG, "TrendingAdapter: adapter is working");
    }

    @NonNull
    @Override
    public TrendingAdapter.trendingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_recycler_view_item, parent, false);
        return new trendingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingAdapter.trendingMovieViewHolder holder, int position) {
        final Result movie = result.get(position);
        glide.with(context).load("http://image.tmdb.org/t/p/w342" + movie.getPosterPath()).into(holder.posterImage);
        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onResultClick(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setMovies(List<Result> result){
        this.result = result;
        notifyDataSetChanged();
    }

    public void setMovieClickListener(mOnClickListener listener) {
        this.listener = listener;
    }

    public class trendingMovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView posterImage;

        public trendingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.trendingMovieImage);
        }
    }
}
