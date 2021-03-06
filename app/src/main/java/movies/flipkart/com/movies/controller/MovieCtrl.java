package movies.flipkart.com.movies.controller;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import movies.flipkart.com.movies.model.MovieDetail;
import movies.flipkart.com.movies.model.MovieItem;
import movies.flipkart.com.movies.model.MovieList;
import movies.flipkart.com.movies.network.MovieRequest;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class MovieCtrl {
    private Gson gson = new Gson();
    private MovieList movieList;
    private String searchString;
    private static MovieCtrl instance;
    private MovieCtrl(){};

    public static MovieCtrl getInstance(){
        if(instance == null)
        {
            synchronized (MovieCtrl.class)
            {
                instance = new MovieCtrl();
            }
        }
        return instance;
    }

    public String getSearchString() {
        return searchString;
    }

    public void getMovies(final String searchQuery,final MoviesListListener moviesListListener){
        searchString = searchQuery;
        MovieRequest.getMovies(searchQuery,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              if(!response.contains("Error")) {
                  movieList = gson.fromJson(response, MovieList.class);
              }
                moviesListListener.dataUpdated();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                moviesListListener.onError();
            }
        });
    }

    public void getMovieDetail(final String movieId,final MovieDetailListener movieDetailListener)
    {

            MovieRequest.getMovieDetail(movieId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    MovieDetail movieDetail = gson.fromJson(response, MovieDetail.class);
                    movieDetailListener.movieDetails(movieDetail);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    movieDetailListener.onError();
                }
            });

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
    }

    public void destroy(){
        instance = null;
    }

    public interface MoviesListListener {
        public void dataUpdated();
        public void onError();
    }
    public interface MovieDetailListener {
        public void movieDetails(MovieDetail detail);
        public void onError();
    }
}
