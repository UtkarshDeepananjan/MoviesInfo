package com.uds.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.uds.popularmovies.adapter.MoviesAdapter;
import com.uds.popularmovies.databinding.ActivityMainBinding;
import com.uds.popularmovies.model.Movie;
import com.uds.popularmovies.utils.Constants;
import com.uds.popularmovies.utils.ItemClickListener;
import com.uds.popularmovies.utils.PreferenceUtils;
import com.uds.popularmovies.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.uds.popularmovies.utils.NetworkUtils.isConnectedToInternet;

public class MainActivity extends AppCompatActivity implements ItemClickListener, PopupMenu.OnMenuItemClickListener {
    private ActivityMainBinding mainBinding;
    private static final String MOVIE_LIST_EXTRA = "movie_list_extra";
    private MoviesAdapter mAdapter;
    private List<Movie> movieList;
    private MoviesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new MoviesAdapter(new ArrayList<>(), this);
        mainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mainBinding.recyclerView.setAdapter(mAdapter);
        mainBinding.tvSort.setText(PreferenceUtils.getSelectedMenu(this));
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        if (savedInstanceState != null) {
            ArrayList<Movie> movieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST_EXTRA);
            mAdapter.setMovieList(movieList);
            mainBinding.shimmer.setVisibility(View.GONE);
        } else {
            if (isConnectedToInternet(this)) {
                mainBinding.shimmer.startShimmer();
                selectMovieCategory();
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection_available), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(int position) {
        Movie movie = movieList.get(position);
        Intent movieDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        movieDetailIntent.putExtra("movie", movie);
        startActivity(movieDetailIntent);
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.movies_popup_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moive_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void selectMovieCategory() {
        String selectedMenu = PreferenceUtils.getSelectedMenu(this);
        switch (selectedMenu) {
            case Constants.POPULAR_MENU:
                getMoviesList(Constants.POPULAR_MENU, viewModel.getPopularMovies());
                break;
            case Constants.TOP_RATED_MENU:
                getMoviesList(Constants.TOP_RATED_MENU, viewModel.getTopRatedMovies());
                break;
            case Constants.FAVORITE_MENU:
                getMoviesList(Constants.FAVORITE_MENU, viewModel.getFavoriteMovies());
                break;
        }
    }

    private void getMoviesList(String category, LiveData<List<Movie>> moviesList) {
        mainBinding.shimmer.setVisibility(View.GONE);
        mainBinding.shimmer.stopShimmer();

        moviesList.observe(this, movies -> {
            movieList = movies;
            mainBinding.tvSort.setText(category);
            mAdapter.setMovieList(movies);
            if (movies.size() > 0 && movies != null)
                mainBinding.tvNoRecordAvailable.setVisibility(View.GONE);
            else
                mainBinding.tvNoRecordAvailable.setVisibility(View.VISIBLE);
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sort) {
            showPopup(findViewById(R.id.menu_sort));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_popular) {
            PreferenceUtils.setSelectedMenu(this, Constants.POPULAR_MENU);
            selectMovieCategory();
            return true;
        } else if (id == R.id.menu_top_rated) {
            PreferenceUtils.setSelectedMenu(this, Constants.TOP_RATED_MENU);
            selectMovieCategory();
            return true;
        } else if (id == R.id.menu_favorite) {
            PreferenceUtils.setSelectedMenu(this, Constants.FAVORITE_MENU);
            selectMovieCategory();
            return true;
        } else
            return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mAdapter != null) {
            List<Movie> movies = mAdapter.getMovieList();
            if (movies != null && !movies.isEmpty()) {
                outState.putParcelableArrayList(MOVIE_LIST_EXTRA, (ArrayList<? extends Parcelable>) movies);
            }
        }
        super.onSaveInstanceState(outState);
    }

}