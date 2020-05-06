package com.divyansh.dflix.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.divyansh.dflix.data.entities.Saved.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Saved {

    private static final String TAG = "saved";
    public static final String TABLE_NAME = "saved";

    @PrimaryKey(autoGenerate = false)
    public int id;
    public String posterPath;
    public String type;

    public Saved(int id, String posterPath, String type) {
        this.id = id;
        this.posterPath = posterPath;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
