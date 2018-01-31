package com.heliossoftwaredeveloper.movieviewer.Main;

import android.content.Intent;
import android.os.Bundle;
import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.Fragment.MovieDetailsFragment;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.Fragment.MovieListFragment;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.MovieDetailsActivity;
import com.heliossoftwaredeveloper.movieviewer.R;
import com.heliossoftwaredeveloper.movieviewer.Utilities.Constant;
import com.heliossoftwaredeveloper.movieviewer.Utilities.ImageLazyLoader.ImageLazyLoaderManager;

/**
 * Activity Class for Main Screen
 *
 * Created by rngrajo on 31/01/2018.
 */

public class MainActivity extends BaseFragmentActivity implements MovieListFragment.MovieListFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method to initialize BaseFragmentActivity objects
        initBaseActivity(null);

        //set toolbar appearance
        setToolbarTitle(Constant.TOOLBAR_MOVIELIST);
        setToolbarTextColor(getResources().getColor(R.color.colorAccent));

        //set MovieListFragment callback
        ((MovieListFragment)getSupportFragmentManager().findFragmentById(R.id.movieListFragment)).setCallback(this);
    }

    //method to be called when an Item in MovieListFragment has been clicked
    @Override
    public void onMovieItemClicked(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.movieDetailsFragment);
        if(movieDetailsFragment == null){
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(Movie.class.getName(), movie);
            startActivity(intent);
        }
        else{
            movieDetailsFragment.updateContent(movie);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLazyLoaderManager.getInstance().cancelAllDownloadRequest();
    }
}