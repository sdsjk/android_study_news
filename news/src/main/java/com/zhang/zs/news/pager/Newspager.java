package com.zhang.zs.news.pager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhang.zs.news.activity.MainActivity;
import com.zhang.zs.news.base.BaseLeftMenuPager;
import com.zhang.zs.news.base.Basepage;
import com.zhang.zs.news.bean.NewsCenter;
import com.zhang.zs.news.fragment.LeftFragment;
import com.zhang.zs.news.leftMenu.NewsDetailBasePager;
import com.zhang.zs.news.leftMenu.PictureDetailBasePager;
import com.zhang.zs.news.leftMenu.SwitchDetailBasePager;
import com.zhang.zs.news.leftMenu.TopicDetailBasePager;
import com.zhang.zs.news.utils.Cache;
import com.zhang.zs.news.utils.UrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class Newspager extends Basepage {
    List<NewsCenter.DataBean> data;
    List<BaseLeftMenuPager> leftMenuPagers;

    public Newspager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
//        tv_title.setText("新闻");
//
//        TextView textview = new TextView(context);
//        Log.e("TAG", "新闻被初始化了.........");
//        textview.setText("我是新闻");
//        textview.setTextSize(30);
//        textview.setTextColor(Color.RED);
//        base_content.addView(textview);

        String reslut = Cache.getString(context, UrlUtils.NEWSCENTER);

        if (reslut != null && !"".equals(reslut)) {
            Log.e("TAG", "数据已经缓存");
            progressData(reslut);
        } else {
            getData();

        }


    }

    /**
     * 联网请求数据
     */
    private void getData() {
        RequestParams params = new RequestParams(UrlUtils.NEWSCENTER);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "成功" + result);
                //数据的缓存
                Cache.putString(context, UrlUtils.NEWSCENTER, result);
                progressData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "出错");
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

    //数据的解析
    private void progressData(String result) {
        NewsCenter newsCenter = GetBean(result);

        Log.e("TAG", newsCenter.getData().get(0).getChildren().get(0).getTitle() + "------");
        //传递数据
        /**
         * 1.通过activity来传递数据
         *
         */
        data = newsCenter.getData();
        MainActivity activity = (MainActivity) context;
        LeftFragment leftFragment = activity.getLeftFragment();
        leftFragment.setLeftData(data);

        //初始化 左侧试图

        leftMenuPagers = new ArrayList<>();
        leftMenuPagers.add(new NewsDetailBasePager(context, data.get(0)));
        leftMenuPagers.add(new TopicDetailBasePager(context, data.get(0)));
        leftMenuPagers.add(new PictureDetailBasePager(context));
        leftMenuPagers.add(new SwitchDetailBasePager(context));
        switchpager(0);

    }

    private NewsCenter GetBean(String result) {
        return new Gson().fromJson(result, NewsCenter.class);
    }

    public void switchpager(int currentPostion) {
        BaseLeftMenuPager baseLeftMenuPager = leftMenuPagers.get(currentPostion);
        View view = baseLeftMenuPager.rootView;


        tv_title.setText(data.get(currentPostion).getTitle());
        baseLeftMenuPager.initData();
        base_content.removeAllViews();
        base_content.addView(view);


        if (currentPostion == 2) {
            switchView.setVisibility(View.VISIBLE);
            switchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureDetailBasePager pictureDetailBasePager = (PictureDetailBasePager) leftMenuPagers.get(2);
                    pictureDetailBasePager.changeView(switchView);

                }
            });
        } else {
            switchView.setVisibility(View.GONE);
        }



    }
}
