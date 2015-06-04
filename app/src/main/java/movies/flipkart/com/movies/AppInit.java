package movies.flipkart.com.movies;

import android.content.Context;

import movies.flipkart.com.movies.network.NetworkRequestQueue;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class AppInit {
    private static  AppInit instance;
    private AppInit(){};
    public static AppInit getInstance(){
        if(instance == null)
        {
            synchronized (AppInit.class)
            {
                instance = new AppInit();
            }
        }
        return  instance;
    }

    public AppInit initialize(Context context)
    {
        NetworkRequestQueue.getInstance().initialize(context);
        return instance;
    };

    public void destroy(){
        NetworkRequestQueue.getInstance().destroy();
       // MovieCtrl.getInstance().destroy();
    }
}

