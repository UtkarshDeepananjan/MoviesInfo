package com.uds.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final int movieId;
    private final Application application;

    public DetailsViewModelFactory(int movieId,Application application) {
        this.movieId = movieId;
        this.application=application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(application,movieId);
    }
}
