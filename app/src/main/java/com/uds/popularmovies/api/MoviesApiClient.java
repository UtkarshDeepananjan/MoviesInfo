package com.uds.popularmovies.api;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uds.popularmovies.model.Movie;
import com.uds.popularmovies.model.MovieReview;
import com.uds.popularmovies.model.MovieTrailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.uds.popularmovies.utils.Constants.API_KEY;
import static com.uds.popularmovies.utils.Constants.BASE_URL;

public class MoviesApiClient {
    private static MoviesApi moviesApi;
    private static final MoviesApiClient mInstance = new MoviesApiClient();

    private MoviesApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesApi = retrofit.create(MoviesApi.class);
    }

    public static MoviesApiClient getInstance() {
        return mInstance;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        MutableLiveData<List<Movie>> mutableMovieLiveData = new MutableLiveData<>();
        Call<MovieResponse> call = moviesApi.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@Nullable Call<MovieResponse> call,@Nullable Response<MovieResponse> response) {
                if (response != null && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    mutableMovieLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(@Nullable Call<MovieResponse> call,@Nullable Throwable t) {

            }
        });
        return mutableMovieLiveData;
    }
    public LiveData<List<Movie>> getTopRatedMovies() {
        MutableLiveData<List<Movie>> mutableMovieLiveData = new MutableLiveData<>();
        Call<MovieResponse> call = moviesApi.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@Nullable Call<MovieResponse> call,@Nullable Response<MovieResponse> response) {
                if (response != null && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    mutableMovieLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(@Nullable Call<MovieResponse> call,@Nullable Throwable t) {

            }
        });
        return mutableMovieLiveData;
    }
    public LiveData<List<MovieTrailer>> getMovieTrailers(int movieId) {
        MutableLiveData<List<MovieTrailer>> mutableMovieLiveData = new MutableLiveData<>();
        Call<MovieTrailerResponse> call = moviesApi.getMovieTrailer(movieId,API_KEY);
      call.enqueue(new Callback<MovieTrailerResponse>() {
          @Override
          public void onResponse(@Nullable Call<MovieTrailerResponse> call,@Nullable Response<MovieTrailerResponse> response) {
              if (response != null && response.body() != null) {
                  List<MovieTrailer> movies = response.body().getResults();
                  mutableMovieLiveData.setValue(movies);
              }
          }

          @Override
          public void onFailure(@Nullable Call<MovieTrailerResponse> call, @Nullable Throwable t) {

          }
      });
        return mutableMovieLiveData;
    }
    public LiveData<List<MovieReview>> getReviews(int movieId) {
        MutableLiveData<List<MovieReview>> mutableMovieLiveData = new MutableLiveData<>();
        Call<MovieReviewResponse> call = moviesApi.getMovieReview(movieId,API_KEY);
        call.enqueue(new Callback<MovieReviewResponse>() {
            @Override
            public void onResponse(@Nullable Call<MovieReviewResponse> call,@Nullable Response<MovieReviewResponse> response) {
                if (response != null && response.body() != null) {
                    List<MovieReview> movies = response.body().getResults();
                    mutableMovieLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(@Nullable Call<MovieReviewResponse> call, @Nullable Throwable t) {

            }
        });
        return mutableMovieLiveData;
    }

}
