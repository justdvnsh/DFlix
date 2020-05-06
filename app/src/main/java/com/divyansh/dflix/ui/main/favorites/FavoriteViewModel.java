package com.divyansh.dflix.ui.main.favorites;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.data.AppDatabase;
import com.divyansh.dflix.data.entities.Saved;

import java.util.List;

import javax.inject.Inject;

public class FavoriteViewModel extends ViewModel {

    private static final String TAG = "FavoriteViewModel";
    private LiveData<List<Saved>> saved;

    AppDatabase db;

    @Inject
    public FavoriteViewModel(AppDatabase db) {
        this.db = db;
        saved = db.savedDao().getAllSavedMovies();
        Log.d(TAG, "FavoriteViewModel: working");
    }

    public LiveData<List<Saved>> getSaved() {
        return saved;
    }

    public void remove(Saved saved) {
        new removeAsyncTask(db).execute(saved);
    }

    private static class removeAsyncTask extends AsyncTask<Saved, Void, Void> {

        private AppDatabase db;

        removeAsyncTask(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Saved... saveds) {
            db.savedDao().removeFromSaved(saveds[0]);
            return null;
        }
    }
}
