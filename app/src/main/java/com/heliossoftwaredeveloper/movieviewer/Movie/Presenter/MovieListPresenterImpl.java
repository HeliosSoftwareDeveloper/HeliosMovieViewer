package com.heliossoftwaredeveloper.movieviewer.Movie.Presenter;

import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.Movie.Presenter.Interactor.MovieInteractor;
import com.heliossoftwaredeveloper.movieviewer.Movie.Presenter.Interactor.MovieInteractorImpl;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.MovieListView;
import com.heliossoftwaredeveloper.movieviewer.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.movieviewer.Utilities.Constant;
import com.heliossoftwaredeveloper.movieviewer.Utilities.NetworkTransactionUtility;
import java.util.ArrayList;

/**
 * Presenter impl class for MovieListFragment
 *
 * Created by rngrajo on 30/01/2018.
 */

public class MovieListPresenterImpl implements MovieListPresenter, MovieInteractor.OnGetMoviesCallback{

    private MovieListView movieView;
    MovieInteractor movieInteractor;

    public MovieListPresenterImpl(MovieListView movieView){
        this.movieView = movieView;
        this.movieInteractor = new MovieInteractorImpl();
    }

    @Override
    public void getMovieList() {
        if(movieView == null)
            return;
        movieView.showLoading();
        APIParams apiParams = new APIParams();
        apiParams.setIsHTTPS(false);
        apiParams.setRequestUrl(Constant.API_URL_GET_MOVIES);
        apiParams.setRequestType(NetworkTransactionUtility.REQUEST_TYPE_GET);
        apiParams.setRequestCaller(Constant.API_MODE_GET_MOVIES);
        movieInteractor.getMovies(apiParams,this);
    }

    @Override
    public void onDestroy() {
        if(movieView == null)
            return;
        movieView.onDestroy();
        movieInteractor.onDestroy();
    }

    @Override
    public void onGetMoviesSuccess(ArrayList<Movie> movieArrayList) {
        if(movieView == null)
            return;
        movieView.hideLoading();
        movieView.updateMovieViewList(movieArrayList);
    }

    @Override
    public void onGetMoviesError(String title, String errorMessage, APIParams apiParams) {
        if(movieView == null)
            return;
        movieView.hideLoading();
        movieView.showUserPrompt(title, errorMessage);
    }

    @Override
    public void onGetMoviesCanceled() {
        if(movieView == null)
            return;
        movieView.hideLoading();
    }
}
