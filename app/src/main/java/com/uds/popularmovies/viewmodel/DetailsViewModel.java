package com.uds.popularmovies.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.popularmovies.api.MoviesApiClient;
import com.uds.popularmovies.database.MoviesRepository;
import com.uds.popularmovies.model.Movie;
import com.uds.popularmovies.model.MovieReview;
import com.uds.popularmovies.model.MovieTrailer;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    private final LiveData<List<MovieTrailer>> mTrailers;
    private final LiveData<List<MovieReview>> mReviews;
    private final MoviesRepository repository;

    public DetailsViewModel(Application application, int movieId) {
        mTrailers = MoviesApiClient.getInstance().getMovieTrailers(movieId);
        mReviews = MoviesApiClient.getInstance().getReviews(movieId);
        repository = new MoviesRepository(application);

    }

    public boolean isFavorite(int movieId) {
        return repository.isFavorite(movieId);
    }

    public void addMovieToFavorites(Movie movie) {
        repository.addMovieToFavorites(movie);
    }

    public void removeMovieFromFavorites(Movie movie) {
        repository.deleteFavoriteMovie(movie);
    }

    public void updateFavoriteMovie(int movieId, boolean isFavorite) {
        repository.updateFavoriteMovie(movieId, isFavorite);
    }

    public LiveData<List<MovieTrailer>> getTrailers() {
        return mTrailers;
    }

    public LiveData<List<MovieReview>> getReviews() {
        return mReviews;
    }

}
