package com.uds.popularmovies.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.uds.popularmovies.model.Movie;
import com.uds.popularmovies.utils.AppExecutors;

import java.util.List;

public class MoviesRepository {
    private final MoviesDao movieDao;
    private final AppExecutors appExecutors;

    private final LiveData<List<Movie>> movies;

    public MoviesRepository(Application application) {
        movieDao = MoviesDatabase.getInstance(application).movieDao();
        movies = movieDao.loadAllFavoriteMovies();

        appExecutors = AppExecutors.getExecutorInstance();
    }

    public LiveData<List<Movie>> loadAllFavoriteMovies() {
        return movies;
    }

    public boolean isFavorite(int movieId) {
        return movieDao.isFavorite(movieId);
    }

    public void updateFavoriteMovie(int movieId, boolean isFavorite) {
        appExecutors.getDiskIO().execute(() -> movieDao.updateFavoriteMovie(movieId, isFavorite));
    }

    public void addMovieToFavorites(Movie movie) {
        appExecutors.getDiskIO().execute(() -> movieDao.insertFavoriteMovie(movie));
    }

    public void deleteFavoriteMovie(Movie movie) {
        appExecutors.getDiskIO().execute(() -> movieDao.deleteFavoriteMovie(movie));
    }
}
