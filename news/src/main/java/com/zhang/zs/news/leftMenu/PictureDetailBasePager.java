package com.zhang.zs.news.leftMenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhang.zs.news.R;
import com.zhang.zs.news.base.BaseLeftMenuPager;
import com.zhang.zs.news.bean.PictureBean;
import com.zhang.zs.news.utils.BitmapCacheUtils;
import com.zhang.zs.news.utils.Cache;
import com.zhang.zs.news.utils.NetCacheUtils;
import com.zhang.zs.news.utils.UrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by zs on 2016/6/29.
 */
public class PictureDetailBasePager extends BaseLeftMenuPager {
    private ListView listview;

    private GridView gridview;
    private List<PictureBean.DataBean.NewsBean> news;
    /**
     * 图片缓存工具类
     */
    private BitmapCacheUtils bitmapCacheUtils;


    /**
     * 网络请求图片的handle
     *
     * @param context
     */
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case NetCacheUtils.SUCESS:

                    Bitmap bitmap = (Bitmap) msg.obj;
                    int postion = msg.arg1;
                    Log.e("TAG", "消息接受成功");
                    if (listview != null && listview.isShown()) {
                        ImageView image = (ImageView) listview.findViewWithTag(postion);
                        if (image != null && bitmap != null) {


                            image.setImageBitmap(bitmap);
                        }
                    }
                    if (gridview != null && gridview.isShown()) {
                        ImageView image = (ImageView) gridview.findViewWithTag(postion);
                        if (image != null && bitmap != null) {

                            image.setImageBitmap(bitmap);
                        }

                    }


                    break;
                case NetCacheUtils.FAIL:
                    Log.e("TAG", "消息失败。。。。");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public PictureDetailBasePager(Context context) {
        super(context);
        this.bitmapCacheUtils = new BitmapCacheUtils(handler);

    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.left_picture, null);

        listview = (ListView) view.findViewById(R.id.listview);
        gridview = (GridView) view.findViewById(R.id.gridview);


        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //获取数据

        String cachData = Cache.getString(context, UrlUtils.PICTURE_URL);
        if (!"".equals(cachData) && cachData != null) {
            ProgressData(cachData);
            Log.e("TAG", "有缓存数据");
        } else {

            getDataFromNet();
        }


    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(UrlUtils.PICTURE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e("TAG", "成功" + result);
                ProgressData(result);
//                数据进行缓存
                Cache.putString(context, UrlUtils.PICTURE_URL, result);
                Log.e("TAG", "图组缓存成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "失败" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "取消");
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "完成");
            }
        });

    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void ProgressData(String result) {
        PictureBean bean = GetDataByGson(result);

        //数据解析完成 listview的数据
        news = bean.getData().getNews();

        listview.setAdapter(new MyAdapter());


    }


    private PictureBean GetDataByGson(String result) {
        return new Gson().fromJson(result, PictureBean.class);
    }

    /**
     * 切换试图
     */

    public boolean isListView = true;

    public void changeView(ImageView switchView) {
        if (isListView) {
            //修改为GrideView
            isListView = false;
            listview.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            gridview.setAdapter(new MyAdapter());
            switchView.setImageResource(R.drawable.icon_pic_list_type);


        } else {
            //修改为listview
            isListView = true;
            gridview.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new MyAdapter());
            switchView.setImageResource(R.drawable.icon_pic_grid_type);

        }

    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodle viewHodle;

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.picture_item, null);

                viewHodle = new ViewHodle();
                viewHodle.image = (ImageView) convertView.findViewById(R.id.image);
                viewHodle.textview_title = (TextView) convertView.findViewById(R.id.textview_title);
                convertView.setTag(viewHodle);

            } else {

                viewHodle = (ViewHodle) convertView.getTag();
            }

            viewHodle.textview_title.setText(news.get(position).getTitle());
            //请求url
            String requestUrl = news.get(position).getListimage();
            requestUrl=UrlUtils.url+requestUrl;


//            x.image().bind(viewHodle.image, news.get(position).getListimage()
//                    .replace("http://10.0.2.2:8080", "http://192.168.23.1:8080"));
//                    .replace("http://10.0.2.2:8080", "http://36.110.76.7:8050"));
            //.replace("http://10.0.2.2:8080", "http://192.168.10.122:8080"));

            //设置标志
            viewHodle.image.setTag(position);

            Bitmap bitmap = bitmapCacheUtils.getBitMap(requestUrl, position);
            if(bitmap!=null){
                viewHodle.image.setImageBitmap(bitmap);
            }

            return convertView;
        }
    }

    class ViewHodle {
        ImageView image;
        TextView textview_title;

    }

}
