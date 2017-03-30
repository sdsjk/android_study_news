package com.zhang.zs.news.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.zhang.zs.news.base.Basepage;

/**
 * Created by zs on 2016/6/28.
 */
public class Setingpager extends Basepage {

    public Setingpager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("设置");

        TextView textview=new TextView(context);
        Log.e("TAG", "设置被初始化了.........");
        textview.setText("我是设置");
        textview.setTextSize(30);
        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(Color.RED);
        base_content.addView(textview);
    }
}
