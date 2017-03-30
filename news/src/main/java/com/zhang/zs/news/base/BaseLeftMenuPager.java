package com.zhang.zs.news.base;

import android.content.Context;
import android.view.View;

/**
 * Created by zs on 2016/6/29.
 */
public abstract class BaseLeftMenuPager {
    public Context context;
    public View rootView;
    public BaseLeftMenuPager(Context context){

        this.context=context;
        this.rootView=initView();
    }

    public abstract View initView() ;


    public void initData(){

    }



}
