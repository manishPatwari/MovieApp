package movies.flipkart.com.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import movies.flipkart.com.movies.adapters.DetailGalleryAdapter;
import movies.flipkart.com.movies.controller.MovieCtrl;


public class MovieDetailActivity extends Activity {
    MovieCtrl movieCtrl;
    ViewPager gallery_view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_gallery);
        Intent intent = getIntent();
        int position =  intent.getIntExtra("position", 0);
        movieCtrl = MovieCtrl.getInstance();

        DetailGalleryAdapter detailGalleryAdapter = new DetailGalleryAdapter(this,movieCtrl);
        gallery_view = (ViewPager)findViewById(R.id.movie_detail_gallery);
        gallery_view.setAdapter(detailGalleryAdapter);
        gallery_view.setCurrentItem(position);

    }



}

