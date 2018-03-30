package com.quizline;

import android.content.Context;

/**
 * Created by decimal on 29/3/18.
 */

public class SharedPreferences {

    public static void savePreferences(Context context, String key, String value) {
        android.content.SharedPreferences info = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor agent = info.edit();

        agent.clear();
        agent.commit();
        agent.putString(key, value);
        agent.commit();
        agent = null;
    }

    public static String getperferences(Context context, String key) {
        android.content.SharedPreferences info = context.getSharedPreferences(key, Context.MODE_PRIVATE);

        String value = info.getString(key, "0");
        info = null;
        return value;
    }
}
