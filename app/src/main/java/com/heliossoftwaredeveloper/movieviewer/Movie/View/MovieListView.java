package com.heliossoftwaredeveloper.movieviewer.Movie.View;

import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import java.util.ArrayList;

/**
 * interface for MovieListFragment Callback
 *
 * Created by rngrajo on 30/01/2018.
 */

public interface MovieListView {
    void updateMovieViewList(ArrayList<Movie> movieArrayList);
    void showLoading();
    void hideLoading();
    void showUserPrompt(String title, String message);
    void onDestroy();
}
