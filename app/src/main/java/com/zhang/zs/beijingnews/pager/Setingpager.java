package com.zhang.zs.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.zhang.zs.beijingnews.base.BasePager;

/**
 * Created by zs on 2016/6/29.
 */
public class Setingpager extends BasePager {
    public Setingpager(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        tv_title.setText("设置");
        TextView textView = new TextView(context);
        textView.setText("我是设置页面");
        textView.setTextSize(20);
        textView.setTextColor(Color.RED);
        content.addView(textView);

    }
}
