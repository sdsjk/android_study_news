package com.zhang.zs.news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zs on 2016/7/5.
 */
public class NetCacheUtils {

    public static final int SUCESS = 1;
    public static final int FAIL = 2;
    private final Handler handler;
    /**
     * 本地缓存
     */
    public LocalCacheUtils localeCacheUtils;
    /**
     * 内存缓存
     */
    public MemoryCacheUtils memoryCacheUtils;


    private ExecutorService service;


    /**
     * 构造方法
     *
     * @param handler
     * @param localeCacheUtils
     * @param memoryCacheUtils
     */
    public NetCacheUtils(Handler handler, LocalCacheUtils localeCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.handler = handler;
        this.localeCacheUtils = localeCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;

        service = Executors.newFixedThreadPool(10);


    }

    /**
     * 成功之后发送消息
     *
     * @param requestUrl
     * @param position
     */


    public void getBitMapFromNet(String requestUrl, int position) {

        service.execute(new MyRunbale(requestUrl, position));


    }


    private class MyRunbale implements Runnable {
        private final String requestUrl;
        private final int position;

        public MyRunbale(String requestUrl, int position) {

            this.requestUrl = requestUrl;
            this.position = position;

        }

        @Override
        public void run() {

            try {
                URL url = new URL(requestUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                conn.setReadTimeout(4000);
                conn.setConnectTimeout(4000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

                int code = conn.getResponseCode();

                if (code == 200) {
                    Log.e("TAG", "请求成功");

                    InputStream inputStream = conn.getInputStream();

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                    inputStream.close();
                    /**
                     * 发送消息
                     *
                     */
                    Log.e("TAG", "从本地读取数据网络的数据");
                    Message msg = Message.obtain();

                    msg.what = SUCESS;
                    msg.arg1 = position;
                    msg.obj = bitmap;

                    handler.sendMessage(msg);

                    /**
                     * 存储到本地
                     */
                    localeCacheUtils.putBitmap(requestUrl, bitmap);
                    /**
                     * 存储到内存当中
                     */
                    memoryCacheUtils.putBitMap(requestUrl, bitmap);
                }


            } catch (Exception e) {
                e.printStackTrace();
                /**
                 * 出错发送出错消息
                 *
                 */
                Message msg = Message.obtain();

                msg.what = FAIL;
                msg.arg1 = position;

                handler.sendMessage(msg);

            }


        }
    }
}
