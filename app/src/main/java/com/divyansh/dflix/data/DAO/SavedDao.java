package com.divyansh.dflix.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.divyansh.dflix.data.entities.Saved;

import java.util.List;

@Dao
public interface SavedDao {
    @Query("SELECT * FROM " + Saved.TABLE_NAME)
    LiveData<List<Saved>> getAllSavedMovies();

    @Query("SELECT * FROM " + Saved.TABLE_NAME + " WHERE id IN(:savedIds)")
    public abstract List<Saved> findById(int[] savedIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Saved saved);

    @Delete
    void removeFromSaved(Saved saved);
}
