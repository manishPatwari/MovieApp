package movies.flipkart.com.movies.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import movies.flipkart.com.movies.utils.LruBitmapCache;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class NetworkRequestQueue {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static  NetworkRequestQueue instance;
    private NetworkRequestQueue(){};
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
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(),1024*1024);
        mRequestQueue.start();
        mImageLoader = new ImageLoader(mRequestQueue,new LruBitmapCache(context.getApplicationContext()));
        return instance;
    };

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        mRequestQueue.add(req);
    }

    public void cancelAllRequest(String tag)
    {
        mRequestQueue.cancelAll(tag);
    }

    public void destroy(){
        mRequestQueue.stop();
        mRequestQueue = null;
        instance = null;
        mImageLoader = null;
    }
}
