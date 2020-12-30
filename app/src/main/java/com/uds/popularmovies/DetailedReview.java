package com.uds.popularmovies;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uds.popularmovies.adapter.MovieReviewsAdapter;
import com.uds.popularmovies.databinding.ActivityDetailedReviewBinding;
import com.uds.popularmovies.model.MovieReview;
import com.uds.popularmovies.utils.NetworkUtils;

import java.util.List;

public class DetailedReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailedReviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_review);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.reviews_label));
        }
        if (NetworkUtils.isConnectedToInternet(this)) {
            List<MovieReview> movieReviews = getIntent().getParcelableArrayListExtra("movie_reviews");
            binding.rvReviews.setLayoutManager(new LinearLayoutManager(this));
            binding.rvReviews.setAdapter(new MovieReviewsAdapter(this, movieReviews, true));
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection_available), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}