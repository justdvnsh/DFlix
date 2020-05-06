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
import com.divyansh.dflix.data.entities.Saved;
import com.divyansh.dflix.models.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.savedViewHolder> {

    private static final String TAG = "SavedAdapter";

    private List<Saved> savedItems = new ArrayList<>();
    private Context context;

    @Inject
    public Glide glide;

    private mOnClickListener listener;

    public interface mOnClickListener {
        void onSavedClick(Saved saved);
        void onRemoveClick(Saved saved);
    }

    public SavedAdapter(Context context) {
        this.context = context;

        Log.d(TAG, "SavedAdapter: working");
    }

    @NonNull
    @Override
    public SavedAdapter.savedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_items_recycler_view, parent, false);
        return new savedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.savedViewHolder holder, int position) {
        final Saved saved = savedItems.get(position);
        glide.with(context).load("http://image.tmdb.org/t/p/w500" + saved.getPosterPath()).into(holder.savedImage);
        holder.savedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSavedClick(saved);
                }
            }
        });

        holder.removeSavedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRemoveClick(saved);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedItems.size();
    }

    public void setSavedItems(List<Saved> savedItems) {
        this.savedItems = savedItems;
        notifyDataSetChanged();
    }

    public void setOnClickListener(mOnClickListener listener) {
        this.listener = listener;
    }

    public class savedViewHolder extends RecyclerView.ViewHolder {

        public ImageView savedImage;
        public FloatingActionButton removeSavedItem;

        public savedViewHolder(@NonNull View itemView) {
            super(itemView);
            savedImage = itemView.findViewById(R.id.savedImage);
            removeSavedItem = itemView.findViewById(R.id.removeSavedItem);
        }
    }
}
