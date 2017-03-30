package com.zhang.zs.news.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * Created by zs on 2016/7/5.
 */
public class BitmapCacheUtils {

    /**
     * 网络缓存工具
     */
    private NetCacheUtils netCacheUtils;

    /**
     * 本地缓存工具类
     */
    private LocalCacheUtils localeCacheUtils;

    /**
     * 内存缓存工具类
     */
    private MemoryCacheUtils memoryCacheUtils;

    public BitmapCacheUtils(Handler handler) {
        /**
         * 网络请求需要传送参数  handle 和本地缓存和内存缓存
         */
        this.memoryCacheUtils = new MemoryCacheUtils();
        this.localeCacheUtils = new LocalCacheUtils(memoryCacheUtils);

        this.netCacheUtils = new NetCacheUtils(handler,localeCacheUtils,memoryCacheUtils);
    }

    /**
     * 获取bitmap
     *
     * @param requestUrl
     * @param position
     * @return
     */
    public Bitmap getBitMap(String requestUrl, int position) {
        /**
         * 1.内存中缓存
         */

        if(memoryCacheUtils!=null){

           Bitmap bitmap= memoryCacheUtils.getBitMap(requestUrl);

            if(bitmap!=null){

                Log.e("TAG","从内存中读取数据");
                return bitmap;
            }
        }


        /**
         * 2.文件中缓存
         */

        if(localeCacheUtils!=null){
            Bitmap bitmap=localeCacheUtils.getBitmap(requestUrl);
            if(bitmap!=null){
                Log.e("TAG","从文件中中读取数据");
                return bitmap;
            }

        }



        /**
         * 网络中缓存
         */


        if(netCacheUtils!=null){
            netCacheUtils.getBitMapFromNet(requestUrl,position);
        }


        return null;
    }
}
