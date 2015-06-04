package movies.flipkart.com.movies.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by manish.patwari on 6/4/15.
 */
public class HelperUtil {

    public static void hideKeyBoard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
