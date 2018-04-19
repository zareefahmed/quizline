package com.quizline;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by decimal on 27/3/18.
 */

public class CommonClass {

    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }
}
