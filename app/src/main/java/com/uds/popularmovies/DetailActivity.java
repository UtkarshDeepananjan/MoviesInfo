package com.uds.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;
import com.uds.popularmovies.adapter.MovieReviewsAdapter;
import com.uds.popularmovies.adapter.TrailerAdapter;
import com.uds.popularmovies.database.MoviesDatabase;
import com.uds.popularmovies.databinding.ActivityDetailBinding;
import com.uds.popularmovies.model.Movie;
import com.uds.popularmovies.model.MovieReview;
import com.uds.popularmovies.model.MovieTrailer;
import com.uds.popularmovies.utils.AppExecutors;
import com.uds.popularmovies.utils.ItemClickListener;
import com.uds.popularmovies.viewmodel.DetailsViewModel;
import com.uds.popularmovies.viewmodel.DetailsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements ItemClickListener {
    private ActivityDetailBinding detailBinding;
    private TrailerAdapter trailerAdapter;
    private MovieReviewsAdapter reviewsAdapter;
    private DetailsViewModel viewModel;
    private Movie movie;
    private List<MovieReview> movieReviewsList;
    private List<MovieTrailer> movieTrailers;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.movie_details));
        }
        movie = getIntent().getParcelableExtra("movie");
        trailerAdapter = new TrailerAdapter(new ArrayList<>(), this);
        reviewsAdapter = new MovieReviewsAdapter(this, new ArrayList<>(), false);
        DetailsViewModelFactory factory = new DetailsViewModelFactory(movie.getMovieId(), getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);
        getFavoriteStatus();
        updateUI(movie);
        detailBinding.tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailedReview.class);
            Bundle reviewBundle = new Bundle();
            reviewBundle.putParcelableArrayList("movie_reviews", (ArrayList<? extends Parcelable>) movieReviewsList);
            intent.putExtras(reviewBundle);
            startActivity(intent);
        });
        detailBinding.ivFavorite.setOnClickListener(v -> onFavoriteClicked());
    }

    private void getFavoriteStatus() {
        AppExecutors.getExecutorInstance().getDiskIO().execute(() -> {
            isFavorite = viewModel.isFavorite(movie.getMovieId());
            if (isFavorite) {
                movie = MoviesDatabase.getInstance(this).movieDao().getMovie(movie.getMovieId());
                runOnUiThread(() -> detailBinding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite)));
            } else {
                runOnUiThread(() -> detailBinding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI(Movie movie) {
        detailBinding.trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        detailBinding.rvReview.setLayoutManager(new LinearLayoutManager(this));
        detailBinding.trailerRecyclerView.setAdapter(trailerAdapter);
        detailBinding.rvReview.setAdapter(reviewsAdapter);
        Picasso.get().load(movie.getFullPosterPath()).into(detailBinding.ivMoviePoster);
        detailBinding.tvMovieName.setText(movie.getTitle());
        detailBinding.tvReleaseDate.setText(movie.getReleaseDate());
        detailBinding.tvDescription.setText(movie.getOverview());
        detailBinding.tvRating.setText(String.valueOf(movie.getVoteAverage()));
       /* detailBinding.ivFavorite.setImageDrawable(viewModel.isFavorite(movie.getMovieId()) ?
                ContextCompat.getDrawable(this, R.drawable.ic_favorite) :
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));*/
        getTrailers();
        getReviews();
    }

    private void getTrailers() {
        viewModel.getTrailers().observe(this, movieTrailers -> {
            viewModel.getTrailers().removeObservers(DetailActivity.this);
            if (movieTrailers.size() > 0) {
                this.movieTrailers = movieTrailers;
                trailerAdapter.setMovieTrailers(movieTrailers);
                detailBinding.tvTrailerNotAvailable.setVisibility(View.GONE);
            } else {
                detailBinding.tvTrailerNotAvailable.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getReviews() {
        viewModel.getReviews().observe(this, movieReviews -> {
            viewModel.getReviews().removeObservers(DetailActivity.this);
            if (movieReviews.size() > 0) {
                if (movieReviews.size() >= 5)
                    detailBinding.tvSeeAll.setVisibility(View.VISIBLE);
                this.movieReviewsList = movieReviews;
                detailBinding.tvReviewNotAvialble.setVisibility(View.GONE);
                reviewsAdapter.setMovieReviews(movieReviews);
            } else {
                detailBinding.tvSeeAll.setVisibility(View.GONE);
                detailBinding.tvReviewNotAvialble.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onFavoriteClicked() {
        AppExecutors.getExecutorInstance().getDiskIO().execute(() -> {
            boolean isFavorite = viewModel.isFavorite(movie.getMovieId());
            if (isFavorite) {
                viewModel.removeMovieFromFavorites(movie);
                runOnUiThread(() -> {
                    detailBinding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
                    Toast.makeText(this, getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT).show();
                });
            } else {
                viewModel.addMovieToFavorites(movie);
                runOnUiThread(() -> {
                    detailBinding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                    Toast.makeText(this, getString(R.string.added_to_favorite), Toast.LENGTH_SHORT).show();
                });
            }
            viewModel.updateFavoriteMovie(movie.getMovieId(), !isFavorite);
        });
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(movieTrailers.get(position).getVideoTrailerUrl()));
        startActivity(intent);
    }

}