package movies.flipkart.com.movies.controller;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import movies.flipkart.com.movies.model.MovieDetail;
import movies.flipkart.com.movies.model.MovieItem;
import movies.flipkart.com.movies.model.MovieList;
import movies.flipkart.com.movies.network.MovieRequest;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class MovieCtrl {
    private Gson gson = new Gson();
    private MoviesListListeners moviesListListeners;
    private MovieList movieList;
    private String searchString;
    private static Map<String,MovieDetail>  movieDetails ;

    private static MovieCtrl instance;
    private MovieCtrl(){};

    public static MovieCtrl getInstance(){
        if(instance == null)
        {
            synchronized (MovieCtrl.class)
            {
                instance = new MovieCtrl();
                movieDetails = new HashMap<String,MovieDetail>();
            }
        }
        return instance;
    }
    public String getSearchString() {
        return searchString;
    }


    public  MovieCtrl setDataListener(MoviesListListeners moviesListListeners){
        this.moviesListListeners = moviesListListeners;
        return instance;
    }
    public void getMovies(final String searchQuery){
        searchString = searchQuery;
        MovieRequest.getMovies(searchQuery,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              if(!response.contains("Error")) {
                  movieList = gson.fromJson(response, MovieList.class);
              }
                moviesListListeners.dataUpdated();
                Log.i("Response for Query " + searchQuery,"Success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                moviesListListeners.onError();
                Log.i("Response for Query " + searchQuery, "Failed");
            }
        });
    }

    public void getMovieDetail(final String movieId)
    {
        if(movieDetails.containsKey(movieId))
        {
            moviesListListeners.movieDetails(movieDetails.get(movieId));
        }
        else {
            MovieRequest.getMovieDetail(movieId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    MovieDetail movieDetail = gson.fromJson(response, MovieDetail.class);
                    movieDetails.put(movieId, movieDetail);
                    moviesListListeners.movieDetails(movieDetail);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    moviesListListeners.onError();
                }
            });
        }
    }

    public int getNumberOfMovies()
    {
        if(movieList != null && movieList.getMovieItems() != null) {
            return movieList.getMovieItems().size();
        }
        return  0;
    };

    public MovieItem getMovieItemAtPosition(int position)
    {
        if(movieList != null && movieList.getMovieItems() != null) {
            return movieList.getMovieItems().get(position);
        }
        return null;
    }

    public void clear(){
        movieList = null;
        movieDetails.clear();

    }

    public interface MoviesListListeners{
        public void dataUpdated();
        public void movieDetails(MovieDetail detail);
        public void onError();
    }
}
