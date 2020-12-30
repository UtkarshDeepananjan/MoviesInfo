package com.uds.popularmovies.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uds.popularmovies.model.MovieTrailer;

import java.util.List;

public class MovieTrailerResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> results = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieTrailer> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailer> results) {
        this.results = results;
    }
}
