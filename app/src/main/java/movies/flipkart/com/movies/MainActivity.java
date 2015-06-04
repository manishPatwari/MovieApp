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


public class MainActivity extends Activity {

    private EditText searchQuery;
    private Button searchBtn;
    private ListView movieList;
    private ProgressDialog progressDialog;
    private MovieCtrl movieCtrl;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the application
        AppInit.getInstance().initialize(this);

        //Select all Views
        searchQuery = (EditText) findViewById(R.id.txt_query);
        searchBtn = (Button) findViewById(R.id.btn_search);
        movieList = (ListView) findViewById(R.id.list_movie);

        //Setting ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.searching_msg));
        progressDialog.setCancelable(false);

        movieCtrl = MovieCtrl.getInstance();

        // movieCtrl.clear();
        if(movieCtrl.getSearchString() != null)
        {
            searchQuery.setText(movieCtrl.getSearchString());
            searchQuery.setSelection(searchQuery.getText().length());
        }

        movieListAdapter = new MovieListAdapter(this,getLayoutInflater(),movieCtrl);

        movieList.setAdapter(movieListAdapter);

        movieList.setEmptyView(findViewById(R.id.empty_list_text));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchClick(view);
                // code to hide the soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchQuery.getApplicationWindowToken(), 0);
            }
        });

        // Enter Click Listener
        searchQuery.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            handleSearchClick(v);
                            // code to hide the soft keyboard
                            InputMethodManager imm = (InputMethodManager) getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(searchQuery.getApplicationWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        // Item Click listener
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                handleListItemClick(adapterView, view, i, l);
            }
        });
    }

    public void handleSearchClick(View view)
    {
        String searchQuery = this.searchQuery.getText().toString();
        if(searchQuery != null && searchQuery.trim().length() > 0)
        {
            progressDialog.show();
            movieCtrl.clear();
            movieCtrl.getMovies(searchQuery,new MovieCtrl.MoviesListListeners() {
                @Override
                public void dataUpdated() {
                    movieListAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                @Override
                public void onError() {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

/*   Parameters
    parent -	The AdapterView where the click happened.
    view   -	    The view within the AdapterView that was clicked (this will be a view provided by the adapter)
    position - 	The position of the view in the adapter.
    id	-   The row id of the item that was clicked. */
    public void handleListItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent =  new Intent(this,MovieDetailActivity.class);
        intent.putExtra("position",i);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       progressDialog.dismiss();
        AppInit.getInstance().destroy();
    }

}
