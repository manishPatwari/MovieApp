package movies.flipkart.com.movies.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import movies.flipkart.com.movies.R;
import movies.flipkart.com.movies.controller.MovieCtrl;
import movies.flipkart.com.movies.model.MovieDetail;
import movies.flipkart.com.movies.network.NetworkRequestQueue;

/**
 * Created by manish.patwari on 5/31/15.
 */
public class DetailGalleryAdapter extends PagerAdapter {

    MovieCtrl movieCtrl;
    Context context;
    LayoutInflater layoutInflater;
    int currentPosition;
    public DetailGalleryAdapter(Context context , MovieCtrl movieCtrl){
        this.context = context;
        this.movieCtrl = movieCtrl;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public int getCount() {
        return movieCtrl.getNumberOfMovies();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View galleryLayout =layoutInflater.inflate(R.layout.movie_detail, null);
        String movieId = movieCtrl.getMovieItemAtPosition(position).getId();
        //movieCtrl.getMovieItemAtPosition(i).getId()
        movieCtrl.getMovieDetail(movieId, new MovieCtrl.MoviesListListeners() {
            @Override
            public void dataUpdated() {

            }

            @Override
            public void movieDetails(MovieDetail detail) {
                showMoviewDetails(detail, galleryLayout);
            }

            @Override
            public void onError() {
                Toast.makeText(context.getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(galleryLayout);
        return galleryLayout;
    }

    private void showMoviewDetails(MovieDetail detail,View view)
    {
        view.findViewById(R.id.movie_details).setVisibility(View.VISIBLE);
        ((TextView)view.findViewById(R.id.detail_title)).setText(detail.getTitle());
        ((TextView)view.findViewById(R.id.detail_director)).setText(detail.getDirector());
        ((TextView)view.findViewById(R.id.detail_plot)).setText(detail.getPlot());
        ((TextView)view.findViewById(R.id.detail_actor)).setText(detail.getActors());

        ImageView poster = (ImageView)view.findViewById(R.id.detail_poster);

        NetworkRequestQueue.getInstance().getImageLoader().get(detail.getPoster(), ImageLoader.getImageListener(poster,
                R.mipmap.app_icon, R.mipmap.app_icon));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
