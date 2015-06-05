package movies.flipkart.com.movies.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class NetworkRequestQueue {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static  NetworkRequestQueue instance;
    private NetworkRequestQueue(){};
   // private int MAX_TIME_OUT = 1000;
    public static NetworkRequestQueue getInstance(){
        if(instance == null)
        {
            synchronized (NetworkRequestQueue.class){
                instance = new NetworkRequestQueue();
            }
        }
        return instance;
    }

    public  NetworkRequestQueue initialize(Context context)
    {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(),1024*1024); // 1MB Cache Size
        mRequestQueue.start();
        //mImageLoader = new ImageLoader(mRequestQueue,new LruBitmapCache(context.getApplicationContext()));

        mImageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
        return instance;
    };

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

    public void addToRequestQueue(Request req)
    {

        //req.setRetryPolicy(new DefaultRetryPolicy(MAX_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(req);  // Add request to Queue.
    }

    public void cancelAllRequest(String tag)
    {
        mRequestQueue.cancelAll(tag); // Cancel all request using Tags.
    }

    public void destroy(){
        mRequestQueue.stop(); // Kill all running Threads.
        mRequestQueue = null;
        instance = null;
        mImageLoader = null;
    }
}
