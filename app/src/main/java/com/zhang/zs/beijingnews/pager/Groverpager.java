package com.zhang.zs.beijingnews.pager;

import android.content.Context;

import com.zhang.zs.beijingnews.base.BasePager;

/**
 * Created by zs on 2016/6/29.
 */
public class Groverpager extends BasePager {
    public Groverpager(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        tv_title.setText("政要");
        
    }
}
