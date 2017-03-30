package com.zhang.zs.beijingnews.pager;

import android.content.Context;

import com.zhang.zs.beijingnews.base.BasePager;

/**
 * Created by zs on 2016/6/29.
 */
public class Homepager extends BasePager {
    public Homepager(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        tv_title.setText("首页");
        
    }
}
