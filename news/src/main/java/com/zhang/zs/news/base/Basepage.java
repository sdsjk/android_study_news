package com.zhang.zs.news.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhang.zs.news.R;
import com.zhang.zs.news.activity.MainActivity;

/**
 * Created by zs on 2016/6/28.
 */
public class Basepage {


    public Context context;
    public View view;
    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout base_content;
    public ImageButton switchView;

    public Button edit;

    public Basepage(Context context) {
        this.context = context;
        view = initView();
    }

    public View initView() {
        View view = View.inflate(context, R.layout.basepager, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        base_content = (FrameLayout) view.findViewById(R.id.base_content);
        switchView = (ImageButton) view.findViewById(R.id.switchView);
        edit = (Button) view.findViewById(R.id.edit);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });


        return view;
    }


    public void initData() {

    }

}
