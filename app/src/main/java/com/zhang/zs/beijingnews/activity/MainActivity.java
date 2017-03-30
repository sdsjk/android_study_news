package com.zhang.zs.beijingnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhang.zs.beijingnews.R;
import com.zhang.zs.beijingnews.fragment.Content;
import com.zhang.zs.beijingnews.fragment.LeftMenu;
import com.zhang.zs.beijingnews.util.DensityUtil;

/**
 * Created by zs on 2016/6/28.
 */
public class MainActivity extends SlidingFragmentActivity {

    public static final String CONTENT = "content";
    public static final String LEFT_MENU = "left_menu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //主界面
        setContentView(R.layout.content);
        //设置左侧页面
        setBehindContentView(R.layout.leftmenu);

        SlidingMenu slidingMenu = getSlidingMenu();

//        slidingMenu.setSecondaryMenu(R.layout.leftmenu);

        ///设置模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置触摸的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设主界面的停留的位置
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this, 200));
        initData();
    }

    private void initData() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.left_menu, new LeftMenu(), LEFT_MENU);
        ft.replace(R.id.content, new Content(), CONTENT);
        ft.commit();


    }

    /**
     * 获取左侧菜单
     * @return
     */
    public LeftMenu getLeftMenu() {
        FragmentManager fm = getSupportFragmentManager();
        LeftMenu leftMenu = (LeftMenu) fm.findFragmentByTag(LEFT_MENU);
        return leftMenu;
    }


    public Content getContent() {
        FragmentManager fm = getSupportFragmentManager();
        Content content = (Content) fm.findFragmentByTag(CONTENT);
        return content;
    }
}
