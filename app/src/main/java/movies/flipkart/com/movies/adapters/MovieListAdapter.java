package movies.flipkart.com.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            holder.mTitle = (TextView) view.findViewById(R.id.item_movie_title);
            holder.mYr = (TextView) view.findViewById(R.id.item_movie_yr);
            holder.type = (TextView) view.findViewById(R.id.item_movie_type);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder)view.getTag();
        }

        MovieItem movieItem = (MovieItem)getItem(i);
        holder.type.setText(movieItem.getType() + " - ");
        holder.mTitle.setText(movieItem.getTitle());
        holder.mYr.setText("( " +movieItem.getYear() + " )");
        return view;
    }

    public class Holder{
        private TextView mTitle;
        private TextView mYr;
        private TextView type;
    }
}
