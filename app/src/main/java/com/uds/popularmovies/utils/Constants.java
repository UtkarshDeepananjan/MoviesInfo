package com.uds.popularmovies.utils;

public class Constants {
    public final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public final static String API_KEY = "44ba14242fea4d0b77cf1a0db9d193d9";
    public final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String THUMBNAIL_URL = "https://img.youtube.com/vi/%s/hqdefault.jpg";
    public static final String VIDEO_TRAILER_URL = "https://www.youtube.com/watch?v=%s";
    public static final String POPULAR_MENU="POPULAR";
    public static final String TOP_RATED_MENU="TOP RATED";
    public static final String FAVORITE_MENU="FAVORITE";
    public static final String DATABASE_NAME="movie_database";

    public enum ImageSize {
        w92, w154, w185, w342, w500, w780, original
    }
}
