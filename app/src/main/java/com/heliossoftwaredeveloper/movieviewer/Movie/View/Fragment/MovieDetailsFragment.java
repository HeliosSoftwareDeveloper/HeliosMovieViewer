package com.heliossoftwaredeveloper.movieviewer.Movie.View.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.R;
import com.heliossoftwaredeveloper.movieviewer.Utilities.Constant;
import com.heliossoftwaredeveloper.movieviewer.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.movieviewer.Utilities.ImageLazyLoader.Model.ImageRequestData;

/**
 * Fragment class for Movie Details
 *
 * Created by rngrajo on 30/01/2018.
 */

public class MovieDetailsFragment extends Fragment implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {
    public TextView txtTitle,txtYearReleased,txtSummary;
    public ImageView imgBanner,imgCover;
    public RatingBar ratingBar;
    public RelativeLayout layoutDetailsContainer;
    Drawable drawablePlaceHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        drawablePlaceHolder = ContextCompat.getDrawable(getActivity(), R.drawable.ic_launcher);
        txtTitle = (TextView)view.findViewById(R.id.txtTitle);
        txtYearReleased = (TextView)view.findViewById(R.id.txtYearReleased);
        txtSummary = (TextView)view.findViewById(R.id.txtSummary);
        imgBanner = (ImageView)view.findViewById(R.id.imgBanner);
        imgCover = (ImageView)view.findViewById(R.id.imgCover);
        ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        layoutDetailsContainer = (RelativeLayout)view.findViewById(R.id.layoutDetailsContainer);

        ImageLazyLoaderManager.getInstance().setCallback(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateContent(((Movie)args.getSerializable(Movie.class.getName())));
        }
    }

    public void updateContent(Movie movie){
        txtTitle.setText(movie.getTitle());
        txtYearReleased.setText("Release Date: "+Integer.toString(movie.getYear()));

        ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(imgBanner,Constant.URL_IMAGE_LINK_BACKDROP.replace("[SLUG]",movie.getSlug()),drawablePlaceHolder));
        ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(imgCover,Constant.URL_IMAGE_LINK_COVER.replace("[SLUG]",movie.getSlug()),drawablePlaceHolder));
        txtSummary.setText(fromHtml(getActivity().getString(R.string.movie_details).replace("[rated]", movie.getMpaRating()).replace("[lang]", movie.getLanguage()).replace("[min]",Integer.toString(movie.getRuntime())).replace("[over]",  movie.getOverview())));
        ratingBar.setRating((float)movie.getRating());
        layoutDetailsContainer.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    public void onImageDownloaded(ImageRequestData imageRequestData) {
        imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
    }

    @Override
    public void onImageDownloadError(String error) {

    }
}
