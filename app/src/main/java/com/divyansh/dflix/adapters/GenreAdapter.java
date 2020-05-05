package com.divyansh.dflix.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.divyansh.dflix.R;
import com.divyansh.dflix.models.Genre;
import com.divyansh.dflix.models.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.genreViewHolder> {

    private static final String TAG = "GenreAdapter";

    private Context context;
    private List<Genre> result = new ArrayList<>();

    private mOnClickListener listener;

    public interface mOnClickListener {
        void onGenreClick(Genre result);
    }

    public GenreAdapter(Context context) {
        this.context = context;
        Log.d(TAG, "GenreAdapter: adapter is working");
    }

    @NonNull
    @Override
    public GenreAdapter.genreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_recycler_view_item, parent, false);
        return new genreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull genreViewHolder holder, int position) {
        final Genre genre = result.get(position);
        holder.name.setText(genre.getName());
        holder.genreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGenreClick(genre);
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setGenres(List<Genre> result){
        this.result = result;
        notifyDataSetChanged();
    }

    public void setGenreClickListener(mOnClickListener listener) {
        this.listener = listener;
    }

    public class genreViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CardView genreCard;

        public genreViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.genre_name);
            genreCard = itemView.findViewById(R.id.genre_card);
        }
    }
}
