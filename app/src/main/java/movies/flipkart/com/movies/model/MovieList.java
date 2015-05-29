package movies.flipkart.com.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class MovieList {
    @SerializedName("Search")
    private List<MovieItem> movieItems;
    public List<MovieItem> getMovieItems() {
            return movieItems;
    }

    public void setMovieItems(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
    }

    public void addMovie(MovieItem movieItem)
    {
        movieItems.add(movieItem);
    }
}
