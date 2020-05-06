package com.divyansh.dflix.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.divyansh.dflix.data.DAO.SavedDao;
import com.divyansh.dflix.data.entities.Saved;

@Database(entities = {
        Saved.class
}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SavedDao savedDao();
}
