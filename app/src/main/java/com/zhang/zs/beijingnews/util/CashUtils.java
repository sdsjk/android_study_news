package com.zhang.zs.beijingnews.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zs on 2016/6/29.
 */
public class CashUtils {
    public static void putString(Context context, String newscenter, String result) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("bejingnews",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(newscenter,result).commit();

    }

    public static String getString(Context context, String detail_url) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("bejingnews",Context.MODE_PRIVATE);

        return sharedPreferences.getString(detail_url,"");
    }
}
