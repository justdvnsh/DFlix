package com.divyansh.dflix.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import static com.divyansh.dflix.data.entities.Movies.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Movies {

    private static final String TAG = "Movies";
    public static final String TABLE_NAME = "movies";

    @PrimaryKey(autoGenerate = false)
    public int id;
    public String title;
    public String description;
    public double rating;
    public String posterPath;
    public String placeholder;
    public String releaseDate;
    public int length;
    public String genres;

    public Movies(int id, String title, String description, double rating, String posterPath, String placeholder, String releaseDate, int length, String genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.posterPath = posterPath;
        this.placeholder = placeholder;
        this.releaseDate = releaseDate;
        this.length = length;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
