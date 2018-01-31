package com.heliossoftwaredeveloper.movieviewer.Movie.Presenter.Interactor;

import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.Utilities.API.Model.APIParams;

import java.util.ArrayList;

/**
 * Interface for Movie Interactor
 *
 * Created by rngrajo on 30/01/2018.
 */

public interface MovieInteractor {

    interface OnGetMoviesCallback{
        void onGetMoviesSuccess(ArrayList<Movie> movieArrayList);
        void onGetMoviesError(String title, String errorMessage, APIParams apiParams);
        void onGetMoviesCanceled();
    }

    void getMovies(APIParams apiParams, OnGetMoviesCallback callback);
    void onDestroy();
}
