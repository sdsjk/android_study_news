package com.zhang.zs.beijingnews.leftview;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhang.zs.beijingnews.base.BaseLeft;

/**
 * Created by zs on 2016/6/29.
 */
public class SwitchLeftpager extends BaseLeft {

    public TextView textview;

    public SwitchLeftpager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textview = new TextView(context);
        textview.setTextColor(Color.RED);

        textview.setTextSize(20);
        textview.setGravity(Gravity.CENTER);
        return textview;
    }

    @Override
    public void initData() {

        textview.setText("我是互动");
    }
}
