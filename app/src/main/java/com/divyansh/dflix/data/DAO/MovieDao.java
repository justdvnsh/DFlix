package com.divyansh.dflix.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.divyansh.dflix.data.entities.Movies;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM " + Movies.TABLE_NAME)
    Flowable<Movies> getAllSavedMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Movies movie);

    @Delete
    void removeFromSaved(Movies movie);
}
