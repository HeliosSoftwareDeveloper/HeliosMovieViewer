package com.heliossoftwaredeveloper.movieviewer.Movie.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.R;
import com.heliossoftwaredeveloper.movieviewer.Utilities.Constant;
import com.heliossoftwaredeveloper.movieviewer.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.movieviewer.Utilities.ImageLazyLoader.Model.ImageRequestData;
import java.util.ArrayList;

/**
 * Adapter class for Movielist RecyclerView
 *
 * Created by rngrajo on 30/01/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<Movie> movieArrayList;
    private Activity context;
    private MovieListAdapterCallback callback;

    /**
     * interface for MovieListAdapter callback
     */
    public interface MovieListAdapterCallback{
        void onItemClicked(Movie movie, int position);
    }

    /**
     * Constructor for MovieListAdapter
     *
     * @param callback
     * @param context
     * @param movieArrayList
     *
     */
    public MovieListAdapter(ArrayList<Movie> movieArrayList, Activity context, MovieListAdapterCallback callback) {
        this.movieArrayList = movieArrayList;
        this.context          = context;
        this.callback         = callback;
    }

    /**
     * Class for MovieViewDetails ViewHolder
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle,txtYearReleased;
        public ImageView imgBanner;
        public CardView cardViewContainer;

        public MovieViewHolder(View view ) {
            super(view);
            txtTitle = (TextView)view.findViewById(R.id.txtTitle);
            txtYearReleased = (TextView)view.findViewById(R.id.txtYearReleased);
            imgBanner = (ImageView)view.findViewById(R.id.imgBanner);
            cardViewContainer = (CardView)view.findViewById(R.id.cardViewContainer);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int capsuleType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Movie movie = movieArrayList.get(position);

        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        movieViewHolder.txtTitle.setText(movie.getTitle());
        movieViewHolder.imgBanner.setId(movie.getId());
        movieViewHolder.txtYearReleased.setText("Release Date: "+Integer.toString(movie.getYear()));

        movieViewHolder.cardViewContainer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onItemClicked(movieArrayList.get(position),position);
            }
        });

        ImageLazyLoaderManager.getInstance().setCallback(this);
        ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(movieViewHolder.imgBanner,Constant.URL_IMAGE_LINK_BACKDROP.replace("[SLUG]",movie.getSlug()),ContextCompat.getDrawable(context, R.drawable.ic_launcher)));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    @Override
    public void onImageDownloaded(ImageRequestData imageRequestData) {
        imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
    }

    @Override
    public void onImageDownloadError(String error) {

    }
}