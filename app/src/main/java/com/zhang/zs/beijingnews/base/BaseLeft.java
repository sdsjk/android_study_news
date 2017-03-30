package com.zhang.zs.beijingnews.base;

import android.content.Context;
import android.view.View;

/**
 * Created by zs on 2016/6/29.
 */
public abstract class BaseLeft {
    public Context context;
    public View rootview;

    public BaseLeft(Context context) {
        this.context = context;
        rootview = initView();
    }

    public abstract View initView();


    public void initData() {

    }

}
