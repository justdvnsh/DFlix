package com.divyansh.dflix.ui.main.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.divyansh.dflix.R;
import com.divyansh.dflix.adapters.SavedAdapter;
import com.divyansh.dflix.data.entities.Saved;
import com.divyansh.dflix.ui.detail.DetailMovieActivity;
import com.divyansh.dflix.utils.Constants;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FavoriteFragment extends DaggerFragment implements SavedAdapter.mOnClickListener{

    private static final String TAG = "FavoriteFragment";
    private FavoriteViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    SavedAdapter adapter;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(FavoriteViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_tv_saved);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        subscribeObservers();

        Log.d(TAG, "onViewCreated: View model is working");
    }

    private void subscribeObservers() {
        viewModel.getSaved().observe(getViewLifecycleOwner(), new Observer<List<Saved>>() {
            @Override
            public void onChanged(List<Saved> saveds) {
                if (saveds.size() > 0) {
                    adapter.setSavedItems(saveds);
                    adapter.setOnClickListener(FavoriteFragment.this);
                } else {
                    Toast.makeText(getContext(), "Nothing Saved Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSavedClick(Saved saved) {
        Intent intent =  new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("id", saved.getId());
        intent.putExtra("type", saved.getType());
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(Saved saved) {
        viewModel.remove(saved);
    }
}
