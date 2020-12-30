package com.uds.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.uds.popularmovies.api.MoviesApiClient;
import com.uds.popularmovies.database.MoviesRepository;
import com.uds.popularmovies.model.Movie;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {
    LiveData<List<Movie>> popularMovies;
    LiveData<List<Movie>> topRatedMovies;
    LiveData<List<Movie>> favoriteMovies;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        popularMovies = MoviesApiClient.getInstance().getPopularMovies();
        topRatedMovies = MoviesApiClient.getInstance().getTopRatedMovies();
        MoviesRepository repository=new MoviesRepository(application);
        favoriteMovies=repository.loadAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }
}
