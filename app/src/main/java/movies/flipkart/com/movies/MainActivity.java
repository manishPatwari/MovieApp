package movies.flipkart.com.movies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

        movieCtrl = MovieCtrl.getInstance().setDataListener(new MovieCtrl.MoviesListListeners() {
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
       // movieCtrl.clear();
        if(movieCtrl.getSearchString() != null)
        {
            mSearchQuery.setText(movieCtrl.getSearchString());
            mSearchQuery.setSelection(mSearchQuery.getText().length());
        }
        movieListAdapter = new MovieListAdapter(this,getLayoutInflater(),movieCtrl);

        mMovieList.setAdapter(movieListAdapter);

        mMovieList.setEmptyView(findViewById(R.id.empty_list_text));
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchClick(view);
                // code to hide the soft keyboard
                InputMethodManager      imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchQuery.getApplicationWindowToken(), 0);
            }
        });

        mSearchQuery.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            handleSearchClick(v);
                            // code to hide the soft keyboard
                            InputMethodManager      imm = (InputMethodManager) getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(mSearchQuery.getApplicationWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        mMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                handleListItemClick(adapterView, view, i, l);
            }
        });


    }

    public void handleSearchClick(View view)
    {
        String searchQuery = mSearchQuery.getText().toString();
        if(searchQuery != null && searchQuery.trim().length() > 0)
        {
            mProgressDialog.show();
            movieCtrl.clear();
            movieCtrl.getMovies(searchQuery);
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
