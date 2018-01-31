package com.heliossoftwaredeveloper.movieviewer.Movie.View;

import android.os.Bundle;
import com.heliossoftwaredeveloper.movieviewer.Main.BaseFragmentActivity;
import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.Fragment.MovieDetailsFragment;
import com.heliossoftwaredeveloper.movieviewer.R;
import com.heliossoftwaredeveloper.movieviewer.Utilities.Constant;

/**
 * Activity class for Movie Details
 *
 * Created by rngrajo on 30/01/2018.
 */
public class MovieDetailsActivity extends BaseFragmentActivity implements BaseFragmentActivity.BaseFragmentActivityCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //method to initialize BaseFragmentActivity objects
        initBaseActivity(this);

        //set toolbar appearance
        setToolbarTitle(Constant.TOOLBAR_EMPTY);
        setToolbarNavigationButtonIcon(R.drawable.ic_action_arrow_back);

        //get the movie object from intent extra then send it to the movie details fragment
        if (getIntent().getExtras() != null) {
            Movie movie = (Movie) getIntent().getExtras().getSerializable(Movie.class.getName());
            MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.movieDetailsFragment);
            if(movieDetailsFragment != null)
                movieDetailsFragment.updateContent(movie);
        }
    }

    //method to be called when an ToolbarNavigationButton has been clicked
    @Override
    public void onToolbarNavigationClicked() {
        onBackPressed();
    }
}
