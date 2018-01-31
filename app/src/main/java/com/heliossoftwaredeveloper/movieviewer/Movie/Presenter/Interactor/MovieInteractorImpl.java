package com.heliossoftwaredeveloper.movieviewer.Movie.Presenter.Interactor;

import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Genre;
import com.heliossoftwaredeveloper.movieviewer.Movie.Model.Movie;
import com.heliossoftwaredeveloper.movieviewer.Utilities.API.APIManager;
import com.heliossoftwaredeveloper.movieviewer.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.movieviewer.Utilities.API.Model.APIResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Impl class for MovieInteractor
 *
 * Created by rngrajo on 30/01/2018.
 */

public class MovieInteractorImpl implements MovieInteractor{

    private APIManager apiManagerGetMovies;

    @Override
    public void getMovies(APIParams apiParams, final OnGetMoviesCallback callback) {
        apiManagerGetMovies = new APIManager();
        apiManagerGetMovies.execute(apiParams, new APIManager.APIManagerCallback() {
            @Override
            public void onAPIStarted(APIParams apiParams) {
            }

            @Override
            public void onAPIFinished(Object obj, APIParams apiParams) {
                if(obj == null) {//null response, showErrorMessage dialog
                    callback.onGetMoviesError("Error","Unable to connect to server.",apiParams);
                    return;
                }
                APIResponse apiResponse = (APIResponse)obj;
                switch (apiResponse.getAPI_RESPONSE_CODE()){
                    case 200:
                        try{
                            Movie movie;
                            Genre genre;
                            ArrayList<Movie> movieArrayList = new ArrayList<>();
                            ArrayList<Genre> genreArrayList;

                            JSONObject jsonResponse = new JSONObject(apiResponse.getAPI_RESPONSE());
                            JSONArray jsonArrayMovies = jsonResponse.getJSONObject("data").getJSONArray("movies");
                            for(int i = 0; i < jsonArrayMovies.length(); i++){
                                JSONObject jsonObjectMovie = jsonArrayMovies.getJSONObject(i);
                                movie = new Movie();
                                genreArrayList = new ArrayList<>();

                                JSONArray jsonArrayGenre = jsonObjectMovie.getJSONArray("genres");

                                for(int a = 0; a < jsonArrayGenre.length(); a++){
                                    genre = new Genre();
                                    genre.setName(jsonArrayGenre.optString(a, ""));
                                    genreArrayList.add(genre);
                                }
                                //set values
                                movie.setRating(jsonObjectMovie.optDouble("rating", 0));
                                movie.setGenreArrayList(genreArrayList);
                                movie.setLanguage(jsonObjectMovie.optString("language",""));
                                movie.setTitle(jsonObjectMovie.optString("title",""));
                                movie.setUrl(jsonObjectMovie.optString("url",""));
                                movie.setTitleLong(jsonObjectMovie.optString("title_long",""));
                                movie.setImgDBCode(jsonObjectMovie.optString("imdb_code",""));
                                movie.setId(jsonObjectMovie.optInt("id", 0));
                                movie.setState(jsonObjectMovie.optString("state",""));
                                movie.setYear(jsonObjectMovie.optInt("year", 0));
                                movie.setRuntime(jsonObjectMovie.optInt("runtime", 0));
                                movie.setOverview(jsonObjectMovie.optString("overview",""));
                                movie.setSlug(jsonObjectMovie.optString("slug",""));
                                movie.setMpaRating(jsonObjectMovie.optString("mpa_rating",""));

                                //set movie object to arraylist
                                movieArrayList.add(movie);
                            }
                            callback.onGetMoviesSuccess(movieArrayList);
                        }
                        catch(Exception ex){
                            callback.onGetMoviesError("Error","Unable to parse data.",apiParams);
                        }
                        apiManagerGetMovies = null;
                        break;
                    default:
                        callback.onGetMoviesError("Error",apiResponse.getAPI_ERROR_MESSAGE(),apiParams);
                        apiManagerGetMovies = null;
                        break;
                }

            }

            @Override
            public void onAPICancelled(APIParams apiParams) {
                callback.onGetMoviesCanceled();
                apiManagerGetMovies = null;
            }
        });
    }

    /**
     * method to be called when the context caller has been destroyed.
     * Cancel all pending api request to avoid fatal exception
     */

    @Override
    public void onDestroy() {
        if(apiManagerGetMovies != null){
            apiManagerGetMovies.cancel();
            apiManagerGetMovies = null;
        }
    }
}
