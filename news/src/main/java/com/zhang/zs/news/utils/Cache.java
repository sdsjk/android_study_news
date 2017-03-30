package com.zhang.zs.news.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.view.View;

import com.zhang.zs.news.Welcome;

import org.xutils.common.util.MD5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zs on 2016/6/28.
 */
public class Cache {


    public static void putBoolean(Context context, String isFrist, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lunch", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(isFrist, b).commit();


    }


    public static boolean getBoolean(Context context, String isFrist) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lunch", Context.MODE_PRIVATE);

        boolean flag = sharedPreferences.getBoolean(isFrist, false);
        return flag;
    }

    public static void putString(Context context, String key, String vlues) {

        /**
         * 判断是是不是存在sd卡
         */
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                String fileName = MD5Encoder.encode(key);
                String dir = Environment.getExternalStorageDirectory() + "/bjxw";
                File file = new File(dir, fileName);

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(vlues.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("lunch", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(key, vlues).commit();
        }


    }

    public static String getString(Context context, String key) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(key);
                String dir = Environment.getExternalStorageDirectory() + "/bjxw";
                File file = new File(dir, fileName);
                if (!file.exists()) {
                    return "";
                }

                byte[] b = new byte[1024];

                int length;

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                FileInputStream fileInputStream = new FileInputStream(file);

                while ((length = fileInputStream.read(b)) != -1) {

                    byteArrayOutputStream.write(b, 0, length);
                }


                byteArrayOutputStream.flush();
                fileInputStream.close();
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("lunch", Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        }

        return "";
    }

}

