package com.zhang.zs.news.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhang.zs.news.R;
import com.zhang.zs.news.fragment.ContentFragment;
import com.zhang.zs.news.fragment.LeftFragment;

public class MainActivity extends SlidingFragmentActivity {

    public static final String HOME_FRAGMENT = "home_fragment";
    public static final String LEFT_MENU = "left_menu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        setBehindContentView(R.layout.left);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        int widthh=getWindowManager().getDefaultDisplay().getWidth();
        slidingMenu.setBehindOffset((int) (widthh*0.625));
        initData();
    }

    private void initData() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main, new ContentFragment(), HOME_FRAGMENT);
        ft.replace(R.id.left_menu, new LeftFragment(), LEFT_MENU);
        ft.commit();


    }

    public LeftFragment getLeftFragment() {
        FragmentManager fm = getSupportFragmentManager();

        LeftFragment leftFragment = (LeftFragment) fm.findFragmentByTag(LEFT_MENU);
        return leftFragment;
    }

    /**
     * 获取到ContentFragment
     * @return
     */
    public ContentFragment getContentView() {
        FragmentManager fm = getSupportFragmentManager();

        ContentFragment contentFragment = (ContentFragment) fm.findFragmentByTag(HOME_FRAGMENT);
        return contentFragment;

    }
}
