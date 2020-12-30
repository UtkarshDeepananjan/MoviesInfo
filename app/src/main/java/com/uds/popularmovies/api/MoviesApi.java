package com.uds.popularmovies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);


    @GET("{id}/videos")
    Call<MovieTrailerResponse> getMovieTrailer(@Path("id") int movieId, @Query("api_key") String apiKey);


    @GET("{id}/reviews")
    Call<MovieReviewResponse> getMovieReview(@Path("id") int movieId, @Query("api_key") String apiKey);

}
