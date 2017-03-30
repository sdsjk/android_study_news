package com.zhang.zs.news.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.zhang.zs.news.base.Basepage;

/**
 * Created by zs on 2016/6/28.
 */
public class Homepager extends Basepage {

    public Homepager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("首页");
        Log.e("TAG", "首页被初始化了.........");
        TextView textview=new TextView(context);

        textview.setText("我是首页");
        textview.setTextSize(30);
        textview.setTextColor(Color.RED);
        base_content.addView(textview);
    }
}
