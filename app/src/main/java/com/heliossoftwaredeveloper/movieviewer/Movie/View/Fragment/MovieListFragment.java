package com.heliossoftwaredeveloper.movieviewer.Movie.View.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.Movie.Presenter.MovieListPresenter;
import com.heliossoftwaredeveloper.movieviewer.Movie.Presenter.MovieListPresenterImpl;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.Adapter.MovieListAdapter;
import com.heliossoftwaredeveloper.movieviewer.Movie.View.MovieListView;
import com.heliossoftwaredeveloper.movieviewer.R;
import com.heliossoftwaredeveloper.movieviewer.Utilities.WrapContentLinearLayoutManager;
import java.util.ArrayList;

/**
 * Fragment class for Movie List
 *
 * Created by rngrajo on 30/01/2018.
 */

public class MovieListFragment extends Fragment implements MovieListView,MovieListAdapter.MovieListAdapterCallback,SwipeRefreshLayout.OnRefreshListener {

    private MovieListPresenter presenter;
    private RecyclerView recyclerViewMovies;
    private SwipeRefreshLayout swipeContainer;
    private MovieListFragmentCallback callback;

    public interface MovieListFragmentCallback{
        void onMovieItemClicked(Movie movie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        presenter = new MovieListPresenterImpl(this);

        recyclerViewMovies = (RecyclerView)view.findViewById(R.id.recyclerViewMovies);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        recyclerViewMovies.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    public void setCallback(MovieListFragmentCallback callback){
        this.callback = callback;
    }

    @Override
    public void onRefresh() {

        presenter.getMovieList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void updateMovieViewList(ArrayList<Movie> movieArrayList) {
        recyclerViewMovies.setAdapter(new MovieListAdapter(movieArrayList,getActivity(),this));
    }

    @Override
    public void showLoading() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showUserPrompt(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onItemClicked(Movie movie, int position) {
        callback.onMovieItemClicked(movie);
    }
}
