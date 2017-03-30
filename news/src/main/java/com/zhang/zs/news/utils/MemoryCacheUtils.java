package com.zhang.zs.news.utils;

import android.graphics.Bitmap;
import android.util.Log;

import org.xutils.cache.LruCache;

/**
 * Created by zs on 2016/7/5.
 */
public class MemoryCacheUtils {

private LruCache<String ,Bitmap> lruCache;

    public MemoryCacheUtils(){
        //内存最大的8分之一
        long maxSize=Runtime.getRuntime().maxMemory()/1024/8;

        lruCache=new LruCache<String ,Bitmap>((int) maxSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                /**
                 *返回每张图片的大小
                 */
                return (value.getRowBytes()*value.getHeight())/1024;
            }
        };

    }

    /**
     * 根据路径保存到内存当中
     * @param requestUrl
     * @param bitmap
     */
    public void putBitMap(String requestUrl, Bitmap bitmap) {
        Log.e("TAG","内存中存放数据");
        lruCache.put(requestUrl,bitmap);
    }


    public Bitmap getBitMap(String requestUrl) {

        Log.e("TAG","内存中获取数据");
        return lruCache.get(requestUrl);
    }
}
