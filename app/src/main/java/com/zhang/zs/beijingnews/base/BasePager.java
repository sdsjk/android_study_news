package com.zhang.zs.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhang.zs.beijingnews.R;

/**
 * Created by zs on 2016/6/29.
 */
public class BasePager {

    public Context context;
    public View rootView;
    public TextView tv_title;
    public ImageButton imagebutton;
    public FrameLayout content;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public View initView() {
        //创建一个线性布局

        //加载布局
        View view = View.inflate(context, R.layout.base_pager, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        imagebutton = (ImageButton) view.findViewById(R.id.imagebutton);
        content = (FrameLayout) view.findViewById(R.id.content);
        // 初始化布局


        return view;
    }

    public void initData() {


    }


}
