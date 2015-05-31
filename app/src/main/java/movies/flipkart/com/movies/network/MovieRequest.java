package movies.flipkart.com.movies.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class MovieRequest {
    private static String baseURL = "http://www.omdbapi.com/?";
    private static String movieListURL = "&y=&plot=short&r=json";
    private static String movieDetailURL = "&plot=short&r=json";

    private MovieRequest(){};

    public static void getMovies(String searchQuery,Response.Listener onSuccess , Response.ErrorListener onError){
        StringRequest request = new StringRequest(Request.Method.GET,baseURL+"s="+searchQuery+movieListURL,onSuccess,onError);
        NetworkRequestQueue.getInstance().addToRequestQueue(request);
    }

    public static void getMovieDetail(String movieId,Response.Listener onSuccess , Response.ErrorListener onError){
        StringRequest request = new StringRequest(Request.Method.GET,baseURL+"i="+movieId+movieDetailURL,onSuccess,onError);
        NetworkRequestQueue.getInstance().addToRequestQueue(request);
    }


}
