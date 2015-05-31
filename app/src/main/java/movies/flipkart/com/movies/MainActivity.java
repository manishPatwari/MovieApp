package movies.flipkart.com.movies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import movies.flipkart.com.movies.adapters.MovieListAdapter;
import movies.flipkart.com.movies.controller.MovieCtrl;
import movies.flipkart.com.movies.model.MovieDetail;


public class MainActivity extends Activity {

    private EditText mSearchQuery;
    private Button mSearchBtn;
    private ListView mMovieList;
    private ProgressDialog mProgressDialog;
    private MovieCtrl movieCtrl;
    private MovieListAdapter movieListAdapter;
    private Spinner spinner_type;
    private  HashMap<String,String> tyepMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize the application
        AppInit.getInstance().initialize(this);

        mSearchQuery = (EditText) findViewById(R.id.txt_query);
        mSearchBtn = (Button) findViewById(R.id.btn_search);
        mMovieList = (ListView) findViewById(R.id.list_movie);
        //Setting ProgressDialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Searching...");
        mProgressDialog.setCancelable(false);

        movieCtrl = MovieCtrl.getInstance();
        //movieCtrl.clear();
        if(movieCtrl.getSearchString() != null)
        {
            mSearchQuery.setText(movieCtrl.getSearchString());
        }
        movieListAdapter = new MovieListAdapter(this,getLayoutInflater(),movieCtrl);

        mMovieList.setAdapter(movieListAdapter);

        mMovieList.setEmptyView(findViewById(R.id.empty_list_text));
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchClick(view);
            }
        });
        mMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                handleListItemClick(adapterView, view, i, l);
            }
        });

        String[] type_name = getResources().getStringArray(R.array.types_name);
        String[] type_id = getResources().getStringArray(R.array.types_id);
        tyepMap = new HashMap<String, String>();
        for (int i = 0; i < type_name.length; i++)
        {
            tyepMap.put(type_name[i],type_id[i]);
        }

        spinner_type = (Spinner)findViewById(R.id.type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type_name);
        spinner_type.setAdapter(typeAdapter);
    }

    public void handleSearchClick(View view)
    {
        String searchQuery = mSearchQuery.getText().toString();
        if(searchQuery != null && searchQuery.trim().length() > 0)
        {
            mProgressDialog.show();
            String typeValue = tyepMap.get(spinner_type.getSelectedItem().toString());
            movieCtrl.clear();
            movieCtrl.getMovies(searchQuery, typeValue,new MovieCtrl.MoviesListListeners() {
                @Override
                public void dataUpdated() {
                    movieListAdapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                }

                @Override
                public void movieDetails(MovieDetail detail) {

                }

                @Override
                public void onError() {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void handleListItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent =  new Intent(this,MovieDetailActivity.class);
        intent.putExtra("position",i);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
        AppInit.getInstance().destroy();
    }

}
