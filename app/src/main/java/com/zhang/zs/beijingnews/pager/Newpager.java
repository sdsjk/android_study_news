package com.zhang.zs.beijingnews.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.zhang.zs.beijingnews.activity.MainActivity;
import com.zhang.zs.beijingnews.base.BaseLeft;
import com.zhang.zs.beijingnews.base.BasePager;
import com.zhang.zs.beijingnews.domin.NewsData;
import com.zhang.zs.beijingnews.fragment.LeftMenu;
import com.zhang.zs.beijingnews.leftview.NewLeftpager;
import com.zhang.zs.beijingnews.leftview.PictureLeftpager;
import com.zhang.zs.beijingnews.leftview.SwitchLeftpager;
import com.zhang.zs.beijingnews.leftview.TopicLeftpager;
import com.zhang.zs.beijingnews.util.CashUtils;
import com.zhang.zs.beijingnews.util.UrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/29.
 */
public class Newpager extends BasePager {

    private NewsData newsData;

    private List<BaseLeft> baseLefts;

    public Newpager(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        tv_title.setText("新闻");
        Log.e("TAG", "新闻初始化");

        getData();

    }

    /**
     * 联网请求数据
     */
    private void getData() {
        RequestParams entity = new RequestParams(UrlUtils.NEWSCENTER);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "联网成功" + result);

                //数据缓存
                CashUtils.putString(context, UrlUtils.NEWSCENTER, result);
                Log.e("TAG", "缓存完成");

                progessData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "联网出错" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "联网取消");
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "联网完成");
            }
        });
    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void progessData(String result) {
        newsData = getNewsData(result);

        //将数据传到左侧菜单显示
        MainActivity mainActivity = (MainActivity) context;

        LeftMenu leftMenu = mainActivity.getLeftMenu();

        leftMenu.setData(newsData.getData());
        //初始化左侧试图集合
        baseLefts = new ArrayList<>();
        baseLefts.add(new NewLeftpager(context,newsData.getData().get(0)));
        baseLefts.add(new TopicLeftpager(context));
        baseLefts.add(new PictureLeftpager(context));
        baseLefts.add(new SwitchLeftpager(context));
       // baseLefts.get(0).initData();
        setCurrentShow(0);
    }

    private NewsData getNewsData(String result) {
        return new Gson().fromJson(result, NewsData.class);
    }

    public void setCurrentShow(int currentpostion) {
     BaseLeft baseLeft= baseLefts.get(currentpostion);
        View view=baseLeft.rootview;
        baseLeft.initData();
        content.removeAllViews();
        content.addView(view);
    }
}
