package movies.flipkart.com.movies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import movies.flipkart.com.movies.controller.MovieCtrl;
import movies.flipkart.com.movies.model.MovieDetail;
import movies.flipkart.com.movies.network.NetworkRequestQueue;


public class MovieDetailActivity extends Activity {
    MovieCtrl movieCtrl;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        int position =  intent.getIntExtra("position", 0);
        movieCtrl = MovieCtrl.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading_msg));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String movieId = movieCtrl.getMovieItemAtPosition(position).getId();

        movieCtrl.getMovieDetail(movieId,new MovieCtrl.MovieDetailListeners() {
            @Override
            public void movieDetails(MovieDetail detail) {
                showMoviewDetails(detail);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void showMoviewDetails(MovieDetail detail)
    {
        ((LinearLayout)findViewById(R.id.movie_details)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.detail_movie_title)).setText(detail.getTitle());
        ((TextView)findViewById(R.id.detail_director)).setText(detail.getDirector());
        ((TextView)findViewById(R.id.detail_plot)).setText(detail.getPlot());
        ((TextView)findViewById(R.id.detail_actor)).setText(detail.getActors());

        ImageView poster = (ImageView)findViewById(R.id.detail_poster);
        if(!detail.getPoster().equals("N/A")) {
            Log.i("Poster URL " , detail.getPoster());
            NetworkRequestQueue.getInstance().getImageLoader().get(detail.getPoster(), ImageLoader.getImageListener(poster,
                    R.mipmap.app_icon, R.mipmap.app_icon));
        }
        else
        {
            poster.setImageResource(R.mipmap.app_icon);
        }
        progressDialog.dismiss();
    }
}

