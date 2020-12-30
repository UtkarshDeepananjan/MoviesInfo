package com.uds.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.uds.popularmovies.model.Movie;
import com.uds.popularmovies.utils.Constants;

@Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static MoviesDatabase mInstance;
    public abstract MoviesDao movieDao();

    public static MoviesDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, Constants.DATABASE_NAME)
                        .build();
            }
        }
        return mInstance;
    }
}
