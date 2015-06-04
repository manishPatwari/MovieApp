package movies.flipkart.com.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import movies.flipkart.com.movies.R;
import movies.flipkart.com.movies.controller.MovieCtrl;
import movies.flipkart.com.movies.model.MovieItem;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class MovieListAdapter extends BaseAdapter {
    private MovieCtrl movieCtrl;
    private Context context;
    private LayoutInflater inflater;

    public MovieListAdapter(Context context,LayoutInflater inflater, MovieCtrl movieCtrl){
        this.context = context;
        this.movieCtrl = movieCtrl;
        this.inflater = inflater;
    }
    @Override
    public int getCount() {
        return movieCtrl.getNumberOfMovies();
    }

    @Override
    public Object getItem(int i) {
        return movieCtrl.getMovieItemAtPosition(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;

        if(view == null)
        {
            holder = new Holder();
            view = inflater.inflate(R.layout.movie_items,null);
            holder.imageView = (ImageView)view.findViewById(R.id.item_movie_icon);
            holder.title = (TextView) view.findViewById(R.id.item_movie_title);
            holder.type = (TextView) view.findViewById(R.id.item_movie_type);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder)view.getTag();
        }

        MovieItem movieItem = (MovieItem)getItem(i);
        holder.type.setText(movieItem.getType() + " - ");
        holder.title.setText(movieItem.getTitle() + " ( " + movieItem.getYear() + " )");
        if(movieItem.getType().equals("series"))
        {
            holder.imageView.setImageResource(R.mipmap.series_icon);
        }
        else{
            holder.imageView.setImageResource(R.mipmap.app_icon);
        }
        return view;
    }

    public class Holder{
        private ImageView imageView;
        private TextView title;
        private TextView type;
    }
}
