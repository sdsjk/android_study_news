package com.zhang.zs.news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zs on 2016/7/5.
 */
public class LocalCacheUtils {
    MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
        this.memoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 缓存到本地文件中
     *
     * @param requestUrl
     * @param bitmap
     */
    public void putBitmap(String requestUrl, Bitmap bitmap) {

        try {
            /**
             * 对路径进行MD5加密
             */
            String fileName = MD5Encoder.encode(requestUrl);

            String dir = Environment.getExternalStorageState() + "/bjxw";
            File file = new File(dir, fileName);
            if (!file.getParentFile().exists()) {

                file.getParentFile().mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Bitmap getBitmap(String requestUrl) {
        try {
            String fileName = MD5Encoder.encode(requestUrl);
            String dir = Environment.getExternalStorageState() + "/bjxw";
            File file = new File(dir, fileName);
            FileInputStream fileOutputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fileOutputStream);
            memoryCacheUtils.putBitMap(requestUrl,bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
