package com.zhang.zs.beijingnews.activity;

import android.app.Application;

import org.xutils.x;

/**
 * Created by zs on 2016/6/28.
 */
public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

        x.Ext.setDebug(true);

    }
}
