package com.divyansh.dflix.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.divyansh.dflix.data.DAO.MovieDao;
import com.divyansh.dflix.data.entities.Movies;

@Database(entities = {
        Movies.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
